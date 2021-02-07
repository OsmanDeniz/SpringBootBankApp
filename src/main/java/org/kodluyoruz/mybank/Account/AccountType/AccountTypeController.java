package org.kodluyoruz.mybank.Account.AccountType;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/account/type")
public class AccountTypeController {
    private final AccountTypeService accountTypeService;

    public AccountTypeController(AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public AccountTypeDto create(@Valid @RequestBody AccountTypeDto accountTypeDto) {
        if (accountTypeService.isAccountTypeExists(accountTypeDto.getAccountName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hesap turu zaten acilmis.");

        return accountTypeService.create(accountTypeDto.toAccountType()).toAccountTypeDto();
    }

}
