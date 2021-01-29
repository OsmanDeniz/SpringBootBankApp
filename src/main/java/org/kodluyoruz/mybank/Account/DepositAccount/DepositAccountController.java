package org.kodluyoruz.mybank.Account.DepositAccount;

import org.kodluyoruz.mybank.Account.Type.AccountType;
import org.kodluyoruz.mybank.Account.Type.AccountTypeService;
import org.kodluyoruz.mybank.Customer.Customer;
import org.kodluyoruz.mybank.Customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/customer/{id}/account/type/deposit")
public class DepositAccountController {
    private CustomerService customerService;
    private AccountTypeService accountTypeService;
    private DepositAccountService depositAccountService;

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

        if (!customerService.isCustomerExists(customer_id)) throw new Exception("Kullanici bulunamadi");

        AccountType a = accountTypeService.findAccountByName("Birikim");

        Customer customer = customerService.findById(customer_id);
        customer.setDepositAccount(depositAccount);

        depositAccount.setAccountType(a);

        depositAccountDto = depositAccountService.create(depositAccount).toDepositAccountDto();
        customerService.update(customer);

        return depositAccountDto;
    }
}