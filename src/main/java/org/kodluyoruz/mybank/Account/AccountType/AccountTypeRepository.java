package org.kodluyoruz.mybank.Account.AccountType;

import org.springframework.data.repository.CrudRepository;

public interface AccountTypeRepository extends CrudRepository<AccountType, Integer> {

    AccountType findByAccountName(AccountTypeEnum name);

    boolean existsByAccountName(AccountTypeEnum name);

}
