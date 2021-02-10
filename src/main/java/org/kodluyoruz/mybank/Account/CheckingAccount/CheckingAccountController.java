package org.kodluyoruz.mybank.Account.CheckingAccount;

import org.kodluyoruz.mybank.Account.AccountType.AccountType;
import org.kodluyoruz.mybank.Account.AccountType.AccountTypeEnum;
import org.kodluyoruz.mybank.Account.AccountType.AccountTypeService;
import org.kodluyoruz.mybank.Customer.Customer;
import org.kodluyoruz.mybank.Customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
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

        if (!customerService.isCustomerExists(customer_id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kullanici bulunamadi");

        //TODO: AccountTypeEnum eklendikten sonra kodu tekrar kontrol et verimlilik icin
        AccountType accountType = accountTypeService.findAccountByName(AccountTypeEnum.Vadesiz);
        checkingAccount.setAccountType(accountType);

        checkingAccount.setCustomerId(customerService.findById(customer_id));
        checkingAccountDto = checkingAccountService.create(checkingAccount).toCheckingAccountDto();

        return checkingAccountDto;
    }

    @GetMapping
    public CheckingAccountDto getCheckingAccount(@RequestParam Integer customerId, String checkingAccountIban) {
        Customer customer = customerService.findById(customerId);
        CheckingAccount checkingAccount = checkingAccountService.findByIban(checkingAccountIban);

        if (customer == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Musteri bulunamadi");

        if (checkingAccount == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hesap bulunamadi");

        if (customer.getId() != checkingAccount.getCustomerId().getId())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hesap baska bir musteriye ait.");

        return checkingAccountService.findByIban(checkingAccountIban).toCheckingAccountDto();

    }

    @PutMapping
    @Transactional
    public CheckingAccount updateCheckingAccount(@PathVariable("id") Integer customerId, @Valid @RequestBody CheckingAccountDto checkingAccountDto) {
        Customer customer = customerService.findById(customerId);
        CheckingAccount checkingAccount = checkingAccountService.findCheckingAccountByCustomer_id(customerId);
        if (customer == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Musteri bulunamadi");
        if (checkingAccount.getIban() == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hesap bulunamadi");

        if (customer.getId() != checkingAccount.getCustomerId().getId())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hesap baska bir musteriye ait.");

        checkingAccountDto.setCustomerId(checkingAccount.getCustomerId());
        checkingAccountDto.setCashCard(checkingAccount.getCashCard());
        checkingAccountDto.setCreated_at(checkingAccount.getCreated_at());
        checkingAccountDto.setAccountType(checkingAccount.getAccountType());
        checkingAccountDto.setIban(checkingAccount.getIban());

        return checkingAccountService.update(checkingAccountDto.toCheckingDto());
    }

    @DeleteMapping
    @Transactional
    @ResponseStatus(value = HttpStatus.OK, reason = "Musteri basariyla silindi.")
    public void deleteCheckingAccount(@PathVariable("id") Integer customerId, @RequestParam String iban) {
        CheckingAccount checkingAccount = checkingAccountService.findCheckingAccountByCustomer_id(customerId);

        if (checkingAccount.getIban() == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Secilen musteriye ait hesap bulunamadi");

        if (!checkingAccount.getIban().equals(iban))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Musteri ile iban eslesmedi");

        if (checkingAccount.getBalance() > 0)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Hesapta para oldugu icin bu hesap silinemez.");

        checkingAccountService.deleteById(iban);

    }
}
