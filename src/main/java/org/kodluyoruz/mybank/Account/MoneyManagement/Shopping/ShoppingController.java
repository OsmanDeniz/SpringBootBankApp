package org.kodluyoruz.mybank.Account.MoneyManagement.Shopping;


import lombok.AllArgsConstructor;
import org.kodluyoruz.mybank.Account.Card.BaseCard;
import org.kodluyoruz.mybank.Account.Card.CashCard.CashCard;
import org.kodluyoruz.mybank.Account.Card.CashCard.CashCardService;
import org.kodluyoruz.mybank.Account.Card.CreditCard.CreditCard;
import org.kodluyoruz.mybank.Account.Card.CreditCard.CreditCardService;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccount;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccountService;
import org.kodluyoruz.mybank.Account.MoneyManagement.Converter.MoneyConverter;
import org.springframework.web.bind.annotation.*;

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
        if (baseCard.equals(null)) throw new Exception("Gecersiz Kart Bilgisi.");
        shoppingDto.setCardId(Collections.singleton(baseCard));

        if (baseCard instanceof CashCard) {
            cashCardOperations(shoppingDto, cardNumber, (CashCard) baseCard);
        } else if (baseCard instanceof CreditCard) {
            creditCardOperations(shoppingDto, (CreditCard) baseCard);
        }

        return shoppingService.create(shoppingDto.toShopping()).toShoppingDto();
    }

    private void creditCardOperations(ShoppingDto shoppingDto, CreditCard creditCard) throws Exception {
        if ((!creditCard.getCurrency().equals(shoppingDto.getCurrency())))
            shoppingDto.setPrice(moneyConverter.convertXtoY(shoppingDto.getPrice(), creditCard.getCurrency(), shoppingDto.getCurrency()));
        if (shoppingDto.getPrice() > creditCard.getBalance()) throw new Exception("Yetersiz bakiye");
        creditCard.setBalance(creditCard.getBalance() - shoppingDto.getPrice());
        shoppingDto.setCurrency(creditCard.getCurrency());
    }

    private void cashCardOperations(ShoppingDto shoppingDto, String cardNumber, CashCard cashCard) throws Exception {
        if ((!cashCard.getCurrency().equals(shoppingDto.getCurrency())))
            shoppingDto.setPrice(moneyConverter.convertXtoY(shoppingDto.getPrice(), cashCard.getCurrency(), shoppingDto.getCurrency()));
        if (shoppingDto.getPrice() > cashCard.getBalance()) throw new Exception("Yetersiz bakiye");
        cashCard.setBalance(cashCard.getBalance() - shoppingDto.getPrice());
        shoppingDto.setCurrency(cashCard.getCurrency());

        CheckingAccount checkingAccount = checkingAccountService.findByCashCardCardNumber(cardNumber);
        checkingAccount.setBalance(cashCard.getBalance());
        checkingAccountService.update(checkingAccount);
    }


    private BaseCard isExistsCardAndReturnCard(String cardNumber) {
        if (cashCardService.isExists(cardNumber)) return cashCardService.findByCardNumber(cardNumber);
        if (creditCardService.isExists(cardNumber)) return creditCardService.findByCardNumber(cardNumber);
        return null;
    }


}
