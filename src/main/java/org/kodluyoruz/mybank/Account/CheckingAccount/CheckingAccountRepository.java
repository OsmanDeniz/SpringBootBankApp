package org.kodluyoruz.mybank.Account.CheckingAccount;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CheckingAccountRepository extends CrudRepository<CheckingAccount, UUID> {

}
