package org.kodluyoruz.mybank.Account.MoneyManagement.Transfer;

import org.kodluyoruz.mybank.Account.MoneyManagement.Converter.MoneyCurrency;
import org.kodluyoruz.mybank.Customer.Customer;
import org.kodluyoruz.mybank.Customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer/{customerId}/account/checking/transfer")
public class MoneyTransferController {
    private final MoneyTransferService transferService;
    private final CustomerService customerService;
    private final MoneyCurrency moneyCurrency;

    public MoneyTransferController(MoneyTransferService transferService, CustomerService customerService, MoneyCurrency moneyCurrency) {
        this.transferService = transferService;
        this.customerService = customerService;
        this.moneyCurrency = moneyCurrency;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MoneyTransferDto create(@PathVariable int customerId, @RequestParam String targetiban, @RequestBody MoneyTransferDto moneyTransferDto) throws Exception {
        Customer sourceCustomer = customerService.findById(customerId);
        Customer targetCustomer = customerService.findByIban(targetiban);
        String targetCurrency = targetCustomer.getCheckingAccount().getCurrency();
        Double amount = 0.0;

        CompositeTransferIdentity compositeTransferIdentity = new CompositeTransferIdentity();
        compositeTransferIdentity.setSourceIban(sourceCustomer.getCheckingAccount().getIban());
        compositeTransferIdentity.setTargetIban(targetiban);

        String sourceCurrency = sourceCustomer.getCheckingAccount().getCurrency();

        if (!sourceCurrency.equals(targetCurrency)) {
            amount = moneyCurrency.convertXtoY(moneyTransferDto.getAmount(), targetCurrency, sourceCurrency);
            moneyTransferDto.setAmount(amount);
        }
        moneyTransferDto.setTransferIdentity(compositeTransferIdentity);

        if (sourceCustomer.getCheckingAccount().getBalance() < moneyTransferDto.getAmount()) {
            throw new Exception("Yetersiz bakiye");
        }

        sourceCustomer.getCheckingAccount().setBalance(sourceCustomer.getCheckingAccount().getBalance() - moneyTransferDto.getAmount());
        targetCustomer.getCheckingAccount().setBalance(targetCustomer.getCheckingAccount().getBalance() + moneyTransferDto.getAmount());

        return transferService.create(moneyTransferDto.toMoneyTransfer()).toMoneyTransferDto();
    }

}
