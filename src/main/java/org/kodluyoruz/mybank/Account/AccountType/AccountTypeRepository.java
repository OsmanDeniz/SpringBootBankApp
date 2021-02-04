package org.kodluyoruz.mybank.Account.AccountType;

import org.springframework.data.repository.CrudRepository;

public interface AccountTypeRepository extends CrudRepository<AccountType, Integer> {

    AccountType findByAccountName(String name);

    boolean existsByAccountName(String name);

}
