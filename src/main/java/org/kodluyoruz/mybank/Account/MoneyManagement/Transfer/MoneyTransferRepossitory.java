package org.kodluyoruz.mybank.Account.MoneyManagement.Transfer;

import org.springframework.data.repository.CrudRepository;

public interface MoneyTransferRepossitory extends CrudRepository<MoneyTransfer, CompositeTransferIdentity> {
}
