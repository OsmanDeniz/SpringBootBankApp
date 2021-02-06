package org.kodluyoruz.mybank.Account.MoneyManagement.Transfer;

import lombok.AllArgsConstructor;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccount;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccountService;
import org.kodluyoruz.mybank.Account.MoneyManagement.Converter.MoneyConverter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/v1/customer/{customerId}/account/checking/transfer")
@AllArgsConstructor
public class MoneyTransferController {
    private final MoneyTransferService transferService;
    private final CheckingAccountService checkingAccountService;
    private final MoneyConverter moneyConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MoneyTransferDto create(@PathVariable int customerId, @RequestParam String sourceiban, @RequestParam String targetiban, @RequestBody MoneyTransferDto moneyTransferDto) throws Exception {

        CheckingAccount sourceCustomerAccount = checkingAccountService.findByIban(sourceiban);
        CheckingAccount targetCustomerAccount = checkingAccountService.findByIban(targetiban);

        checkAccountsInfo(customerId, sourceCustomerAccount, targetCustomerAccount);

        Double amount = 0.0;
        CompositeTransferIdentity compositeTransferIdentity = new CompositeTransferIdentity();
        compositeTransferIdentity.setSourceIban(sourceiban);
        compositeTransferIdentity.setTargetIban(targetiban);

        String sourceCurrency = sourceCustomerAccount.getCurrency();
        String targetCurrency = targetCustomerAccount.getCurrency();

        moneyTransferDto.setSourceCurrency(sourceCurrency);
        moneyTransferDto.setTargetCurrency(targetCurrency);
        moneyTransferDto.setTransferIdentity(compositeTransferIdentity);

        if (!sourceCurrency.equals(targetCurrency)) {
            amount = moneyConverter.convertXtoY(moneyTransferDto.getAmount(), targetCurrency, sourceCurrency);
        }

        sourceCustomerAccount.setBalance(sourceCustomerAccount.getBalance() - moneyTransferDto.getAmount());

        if (sourceCustomerAccount.getBalance() < moneyTransferDto.getAmount())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Yetersiz Bakiye");

        if ((!sourceCurrency.equals(targetCurrency))) {
            targetCustomerAccount.setBalance(targetCustomerAccount.getBalance() + amount);
        } else {
            targetCustomerAccount.setBalance(targetCustomerAccount.getBalance() + moneyTransferDto.getAmount());
        }

        return transferService.create(moneyTransferDto.toMoneyTransfer()).toMoneyTransferDto();
    }

    private void checkAccountsInfo(int customerId, CheckingAccount sourceCustomerAccount, CheckingAccount targetCustomerAccount) {
        if (sourceCustomerAccount == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gonderici hesabi bulunamadi");

        if (sourceCustomerAccount.getCustomer_id().getId() != customerId)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gonderici ile vadesiz hesap bilgileri arasinda uyumsuzluk");

        if (targetCustomerAccount == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alici hesabi bulunamadi");
    }

}
