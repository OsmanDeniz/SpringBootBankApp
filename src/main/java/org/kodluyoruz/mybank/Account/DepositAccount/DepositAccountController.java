package org.kodluyoruz.mybank.Account.DepositAccount;

import org.kodluyoruz.mybank.Account.AccountType.AccountType;
import org.kodluyoruz.mybank.Account.AccountType.AccountTypeService;
import org.kodluyoruz.mybank.Customer.Customer;
import org.kodluyoruz.mybank.Customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("api/v1/customer/{id}/account/type/deposit")
public class DepositAccountController {
    private final CustomerService customerService;
    private final AccountTypeService accountTypeService;
    private final DepositAccountService depositAccountService;

    public DepositAccountController(CustomerService customerService, AccountTypeService accountTypeService, DepositAccountService depositAccountService) {
        this.customerService = customerService;
        this.accountTypeService = accountTypeService;
        this.depositAccountService = depositAccountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepositAccountDto create(@PathVariable("id") int customer_id, @Valid @RequestBody DepositAccountDto dDto) throws Exception {
        DepositAccountDto depositAccountDto = null;
        DepositAccount depositAccount = dDto.toDepositAccount();

        if (!customerService.isCustomerExists(customer_id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kullanici bulunamadi");

        AccountType a = accountTypeService.findAccountByName("Birikim");

        Customer customer = customerService.findById(customer_id);
        customer.setDepositAccount(Collections.singleton(depositAccount));

        depositAccount.setAccountType(a);

        depositAccountDto = depositAccountService.create(depositAccount).toDepositAccountDto();
        customerService.update(customer);

        return depositAccountDto;
    }
}