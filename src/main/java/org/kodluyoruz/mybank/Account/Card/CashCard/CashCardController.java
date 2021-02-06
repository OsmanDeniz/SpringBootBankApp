package org.kodluyoruz.mybank.Account.Card.CashCard;

import lombok.AllArgsConstructor;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccount;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/customer/{id}/account/checking/{iban}/cashcard")
@AllArgsConstructor
public class CashCardController {
    private final CashCardService cardService;
    private final CheckingAccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CashCardDto create(@PathVariable("iban") String iban, @Valid @RequestBody CashCardDto cardDto) throws Exception {
        if (!accountService.isAccountExists(iban))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id'ye ait musteri bulunamadi");

        CheckingAccount checkingAccount = accountService.findByIban(iban);
        cardDto.setCurrency(checkingAccount.getCurrency());
        CashCardDto cashCardDto = cardService.create(cardDto.toCashCard()).toCashCardDto();

        checkingAccount.setCashCard(cashCardDto.toCashCard());
        accountService.update(checkingAccount);

        return cashCardDto;
    }


}
