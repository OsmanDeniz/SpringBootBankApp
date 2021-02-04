package org.kodluyoruz.mybank.Account.MoneyManagement.Transfer;

import org.springframework.stereotype.Service;

@Service
public class MoneyTransferService {
    private final MoneyTransferRepossitory transferRepossitory;

    public MoneyTransferService(MoneyTransferRepossitory transferRepossitory) {
        this.transferRepossitory = transferRepossitory;
    }

    public MoneyTransfer create(MoneyTransfer moneyTransfer) {
        return transferRepossitory.save(moneyTransfer);
    }

}
