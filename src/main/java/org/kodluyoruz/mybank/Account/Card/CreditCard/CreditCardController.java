package org.kodluyoruz.mybank.Account.Card.CreditCard;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccount;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccountService;
import org.kodluyoruz.mybank.Account.MoneyManagement.Converter.MoneyConverter;
import org.kodluyoruz.mybank.Account.MoneyManagement.Shopping.Shopping;
import org.kodluyoruz.mybank.Account.MoneyManagement.Shopping.ShoppingService;
import org.kodluyoruz.mybank.Customer.Customer;
import org.kodluyoruz.mybank.Customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer/{id}/creditcard")
@AllArgsConstructor
public class CreditCardController {
    private final CreditCardService cardService;
    private final CustomerService customerService;
    private final ShoppingService shoppingService;
    private final CheckingAccountService checkingAccountService;
//    MoneyConverter moneyConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCardDto create(@PathVariable("id") int customer_id, @Valid @RequestBody CreditCardDto cardDto) throws Exception {
        if (!customerService.isCustomerExists(customer_id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id'ye ait musteri bulunamadi");

        Customer c = customerService.findById(customer_id);
        cardDto.setCreditCardCustomer(c);

        return cardService.create(cardDto.toCreditCard()).toCreditCardDto();

    }

    @GetMapping
    public CreditCard getCreditCardInfo(@PathVariable(name = "id") Integer customerId, @RequestParam String cardNumber) {
        CreditCard creditCard = cardService.findByCardNumber(cardNumber);

        if (creditCard == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kart bulunamadi");

        if (creditCard.getCreditCardCustomer().getId() != customerId)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sorgulanan kart musteriye ait degil");

        return creditCard;
    }

    @DeleteMapping
    public void deleteCreditCard(@RequestParam String creditCardNumber, @PathVariable("id") int customer_id) {

        CreditCard creditCard = cardService.findByCardNumber(creditCardNumber);

        if (creditCard == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kredi karti bulunamadi.");

        if (creditCard.getCreditCardCustomer().getId() != customer_id)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kredi karti ile musteri uyumsuz");

        if (creditCard.getDebt() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kredi kartina ait borc oldugundan bu kart silinemez.");
        }

        creditCard.setCreditCardCustomer(null);
        cardService.update(creditCard);

        cardService.deleteCreditCard(creditCardNumber);
    }

    @GetMapping("/status")
    public List<Shopping> getCreditCardStatus(@PathVariable(name = "id") Integer custumerId, @RequestParam(name = "period") Integer debtPeriod, @RequestParam String cardNumber) {

        if (!(debtPeriod instanceof Integer))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Borc donemi sayisal olarak ay bilgisini icermelidir.");

        if (debtPeriod > 12 || debtPeriod < 0)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ay bilgisi 1 - 12 arasinda olmalidir.");

        CreditCard creditCard = cardService.findByCardNumber(cardNumber);

        if (creditCard.getCreditCardCustomer().getId() != custumerId)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kredi karti ile musteri uyumsuz");

        List<Shopping> shoppingList = shoppingService.findByCardId_cardNumber(cardNumber);

        List<Shopping> filteredShoppingList = new ArrayList<>();

        for (Shopping shopping : shoppingList) {
            if (debtPeriod == shopping.getProductDateTime().getMonth().getValue())
                filteredShoppingList.add(shopping);
        }

        return filteredShoppingList;
    }

    @GetMapping("/debt")
    public Double creditCardDebt(@RequestParam String cardNumber) {
        CreditCard creditCard = cardService.findByCardNumber(cardNumber);
        if (creditCard == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kredi karti bulunamadi.");

        return creditCard.getDebt();
    }

    @PostMapping("/debt/atm")
    @ResponseStatus(value = HttpStatus.OK, reason = "ATM'den kredi karti odemesi basariyla gerceklesti.")
    public void payCreditCardDebtFromATM(@PathVariable(name = "id") Integer customerId, @RequestParam String cardNumber, @RequestParam Double price) {
        CreditCard creditCard = cardService.findByCardNumber(cardNumber);

        if (creditCard == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kart bulunamadi");

        if (creditCard.getCreditCardCustomer().getId() != customerId)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Odeme yapilacak kart musteriye ait degil");

        if (price <= 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lutfen para yatirin.");

        creditCard.setBalance(creditCard.getBalance() + price);
        creditCard.setDebt(creditCard.getDebt() - price);
        cardService.update(creditCard);
    }

    @PostMapping("/debt/checking")
    @ResponseStatus(value = HttpStatus.OK, reason = "Hesaptan kredi karti odemesi basariyla gerceklesti.")
    @Transactional
    public void payCreditCardDebtFromCheckingAccount(@PathVariable(name = "id") Integer customerId, @RequestParam String cardNumber, @RequestParam String checkingAccountIban, @RequestParam Double price) throws JsonProcessingException {
        CreditCard creditCard = cardService.findByCardNumber(cardNumber);
        CheckingAccount checkingAccount = checkingAccountService.findByIban(checkingAccountIban);

        if (creditCard == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kart bulunamadi");

        if (creditCard.getCreditCardCustomer().getId() != customerId)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Odeme yapilacak kart musteriye ait degil");

        if (price > checkingAccount.getBalance())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hesap bakiyesi yetersiz.");


        if (checkingAccount.getCurrency() != creditCard.getCurrency())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Hesap para birimi ile kart para birimi ayni olmalidir.");
            //price = moneyConverter.convertXtoY(price, checkingAccount.getCurrency(), creditCard.getCurrency());

        if (price > creditCard.getDebt())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kart borcundan fazla odeme yapilamaz. \n Guncel borc : " + creditCard.getDebt());

        creditCard.setBalance(creditCard.getBalance() + price);
        creditCard.setDebt(creditCard.getDebt() - price);
        cardService.update(creditCard);

        checkingAccount.setBalance(checkingAccount.getBalance() - price);
        checkingAccountService.update(checkingAccount);


    }


}
