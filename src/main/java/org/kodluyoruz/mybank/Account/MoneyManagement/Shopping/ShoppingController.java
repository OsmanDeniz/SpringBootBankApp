package org.kodluyoruz.mybank.Account.MoneyManagement.Shopping;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.kodluyoruz.mybank.Account.Card.BaseCard;
import org.kodluyoruz.mybank.Account.Card.CashCard.CashCard;
import org.kodluyoruz.mybank.Account.Card.CashCard.CashCardService;
import org.kodluyoruz.mybank.Account.Card.CreditCard.CreditCard;
import org.kodluyoruz.mybank.Account.Card.CreditCard.CreditCardService;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccount;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccountService;
import org.kodluyoruz.mybank.Account.MoneyManagement.Converter.MoneyConverter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

@RestController
@RequestMapping("api/v1/customer/{customerId}/shopping")
@AllArgsConstructor
public class ShoppingController {
    private final ShoppingService shoppingService;
    private final CashCardService cashCardService;
    private final CreditCardService creditCardService;
    private final MoneyConverter moneyConverter;
    private final CheckingAccountService checkingAccountService;

    @PostMapping
    public ShoppingDto create(@RequestBody ShoppingDto shoppingDto, @RequestParam String cardNumber) throws Exception {
        BaseCard baseCard = isExistsCardAndReturnCard(cardNumber);
        if (baseCard == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gecersiz Kart Bilgisi");
        shoppingDto.setCardId(Collections.singleton(baseCard));

        if (baseCard instanceof CashCard) {
            cashCardOperations(shoppingDto, cardNumber);
        } else if (baseCard instanceof CreditCard) {
            creditCardOperations(shoppingDto, (CreditCard) baseCard);
        }

        return shoppingService.create(shoppingDto.toShopping()).toShoppingDto();
    }

    private void cashCardOperations(ShoppingDto shoppingDto, String cardNumber) throws JsonProcessingException {
        CheckingAccount cashCardAccount = checkingAccountService.findByCashCardCardNumber(cardNumber);
        if ((!cashCardAccount.getCurrency().equals(shoppingDto.getCurrency())))
            shoppingDto.setPrice(moneyConverter.convertXtoY(shoppingDto.getPrice(), cashCardAccount.getCurrency(), shoppingDto.getCurrency()));
        if (shoppingDto.getPrice() > cashCardAccount.getBalance())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Yetersiz Bakiye");
        cashCardAccount.setBalance(cashCardAccount.getBalance() - shoppingDto.getPrice());
        shoppingDto.setCurrency(cashCardAccount.getCurrency());

        CheckingAccount checkingAccount = checkingAccountService.findByCashCardCardNumber(cardNumber);
        checkingAccount.setBalance(cashCardAccount.getBalance());
        checkingAccountService.update(checkingAccount);
    }

    private void creditCardOperations(ShoppingDto shoppingDto, CreditCard creditCard) throws Exception {
        if ((!creditCard.getCurrency().equals(shoppingDto.getCurrency())))
            shoppingDto.setPrice(moneyConverter.convertXtoY(shoppingDto.getPrice(), creditCard.getCurrency(), shoppingDto.getCurrency()));
        if (shoppingDto.getPrice() > creditCard.getBalance())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Yetersiz Bakiye");
        creditCard.setBalance(creditCard.getBalance() - shoppingDto.getPrice());
        shoppingDto.setCurrency(creditCard.getCurrency());
    }


    private BaseCard isExistsCardAndReturnCard(String cardNumber) {
        if (cashCardService.isExists(cardNumber)) return cashCardService.findByCardNumber(cardNumber);
        if (creditCardService.isExists(cardNumber)) return creditCardService.findByCardNumber(cardNumber);
        return null;
    }

}
