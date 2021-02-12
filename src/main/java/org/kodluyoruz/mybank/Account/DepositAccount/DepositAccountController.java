package org.kodluyoruz.mybank.Account.DepositAccount;

import lombok.AllArgsConstructor;
import org.kodluyoruz.mybank.Account.AccountType.AccountTypeEnum;
import org.kodluyoruz.mybank.Account.AccountType.AccountTypeService;
import org.kodluyoruz.mybank.Customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/v1/customer/{id}/account/type/deposit")
@AllArgsConstructor
public class DepositAccountController {
    private final CustomerService customerService;
    private final AccountTypeService accountTypeService;
    private final DepositAccountService depositAccountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepositAccount create(@PathVariable("id") int customer_id, @RequestBody DepositAccountDto dDto) throws Exception {

        if (!customerService.isCustomerExists(customer_id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kullanici bulunamadi");

        dDto.setAccountType(accountTypeService.findAccountByName(AccountTypeEnum.Birikim));
        dDto.setCustomerId(customerService.findById(customer_id));
        return depositAccountService.create(dDto.toDepositAccount());
    }

    @GetMapping("/interest")
    public Double getDepositValue(@PathVariable("id") Integer customer_id, @RequestParam String accountiban) {
        DepositAccount depositAccount = depositAccountService.findDepositAccountByIban(accountiban);
        if (customer_id != depositAccount.getCustomerId().getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hesap ilgili musteriye ait degil.");
        }

//      “anapara*faiz oranı*vade (gün)/36500”
        return (depositAccount.getBalance() + (depositAccount.getBalance() * depositAccount.getInterest_rate() * depositAccount.getInvestment_day() / 36500));
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.OK, reason = "Hesap basariyla silindi.")
    public void deleteDepositAccount(@PathVariable("id") Integer customerId, @RequestParam String accountiban) {
        DepositAccount depositAccount = depositAccountService.findDepositAccountByIban(accountiban);

        if (depositAccount.getCustomerId().getId() != customerId)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Silinecek hesap baska musteriye ");

        depositAccountService.deleteDepositAccount(accountiban);
    }

}