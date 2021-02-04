package org.kodluyoruz.mybank.Account.Card.CashCard;

import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccount;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/customer/{id}/account/checking/{iban}/cashcard")

public class CashCardController {
    private final CashCardService cardService;
    private final CheckingAccountService accountService;

    public CashCardController(CashCardService cardService, CheckingAccountService accountService) {
        this.cardService = cardService;
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CashCardDto create(@PathVariable("iban") String iban, @Valid @RequestBody CashCardDto cardDto) throws Exception {
        if (!accountService.isAccountExists(iban)) throw new Exception("Ibana ait hesap bulunamadi");

        CheckingAccount checkingAccount = accountService.findByIban(iban);
        cardDto.setBalance(checkingAccount.getBalance());
        CashCardDto cashCardDto = cardService.create(cardDto.toCashCard()).toCashCardDto();

        checkingAccount.setCashCard(cashCardDto.toCashCard());
        accountService.update(checkingAccount);

        return cashCardDto;
    }


}
