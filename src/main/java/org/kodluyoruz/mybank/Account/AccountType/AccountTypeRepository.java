package org.kodluyoruz.mybank.Account.AccountType;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountTypeRepository extends CrudRepository<AccountType, Integer> {

    AccountType findByAccountName(AccountTypeEnum name);

    boolean existsByAccountName(AccountTypeEnum name);

    AccountType findAccountTypeById(Integer id);

}
