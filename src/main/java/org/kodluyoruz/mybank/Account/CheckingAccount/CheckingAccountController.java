package org.kodluyoruz.mybank.Account.CheckingAccount;

import org.kodluyoruz.mybank.Account.AccountType.AccountType;
import org.kodluyoruz.mybank.Account.AccountType.AccountTypeService;
import org.kodluyoruz.mybank.Customer.Customer;
import org.kodluyoruz.mybank.Customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/customer/{id}/account/type/checking")
public class CheckingAccountController {
    private final CheckingAccountService checkingAccountService;
    private final CustomerService customerService;
    private final AccountTypeService accountTypeService;

    public CheckingAccountController(CheckingAccountService checkingAccountService, CustomerService customerService, AccountTypeService accountTypeService) {
        this.checkingAccountService = checkingAccountService;
        this.customerService = customerService;
        this.accountTypeService = accountTypeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckingAccountDto create(@PathVariable("id") int customer_id, @Valid @RequestBody CheckingAccountDto dto) throws Exception {
        CheckingAccountDto checkingAccountDto = null;
        CheckingAccount checkingAccount = dto.toCheckingDto();

        if (!customerService.isCustomerExists(customer_id)) throw new Exception("Kullanici bulunamadi");

        AccountType a = accountTypeService.findAccountByName("Vadesiz");

        Customer customer = customerService.findById(customer_id);
        customer.setCheckingAccount(checkingAccount);

        checkingAccount.setAccountType(a);

        checkingAccountDto = checkingAccountService.create(checkingAccount).toCheckingAccountDto();
        customerService.update(customer);

        return checkingAccountDto;
    }
}
