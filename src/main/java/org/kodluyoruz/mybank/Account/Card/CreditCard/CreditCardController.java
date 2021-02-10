package org.kodluyoruz.mybank.Account.Card.CreditCard;

import lombok.AllArgsConstructor;
import org.kodluyoruz.mybank.Account.MoneyManagement.Shopping.Shopping;
import org.kodluyoruz.mybank.Account.MoneyManagement.Shopping.ShoppingService;
import org.kodluyoruz.mybank.Customer.Customer;
import org.kodluyoruz.mybank.Customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCardDto create(@PathVariable("id") int customer_id, @Valid @RequestBody CreditCardDto cardDto) throws Exception {
        if (!customerService.isCustomerExists(customer_id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id'ye ait musteri bulunamadi");

        Customer c = customerService.findById(customer_id);
        cardDto.setCreditCardCustomer(c);

        return cardService.create(cardDto.toCreditCard()).toCreditCardDto();

    }

    @PostMapping("/debt/atm")
    public HttpStatus payCreditCardDebt(@PathVariable(name = "id") Integer customerId, @RequestParam String cardNumber, @RequestParam Double price) {
        CreditCard creditCard = cardService.findByCardNumber(cardNumber);

        if (creditCard == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kart bulunamadi");

        if (creditCard.getCreditCardCustomer().getId() != customerId)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Odeme yapilacak kart musteriye ait degil");

        if (price <= 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lutfen para yatirin.");

        creditCard.setBalance(creditCard.getBalance() + price);
        creditCard.setDebt(creditCard.getDebt() - price);
        cardService.update(creditCard);

        return HttpStatus.OK;
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

    @GetMapping("/debt")
    public Double creditCardDebt(@RequestParam String cardNumber) {
        CreditCard creditCard = cardService.findByCardNumber(cardNumber);
        if (creditCard == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kredi karti bulunamadi.");

        return creditCard.getDebt();
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


}
