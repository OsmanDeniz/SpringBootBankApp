package org.kodluyoruz.mybank.Account.AccountType;

import org.springframework.stereotype.Service;

@Service
public class AccountTypeService {
    private final AccountTypeRepository typeRepository;

    public AccountTypeService(AccountTypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public AccountType create(AccountType accountType) {
        return typeRepository.save(accountType);
    }

    public AccountType findAccountByName(AccountTypeEnum name) {
        return typeRepository.findByAccountName(name);
    }

    public boolean isAccountTypeExists(AccountTypeEnum name) {
        return typeRepository.existsByAccountName(name);
    }

    public AccountType findAccountTypeId(Integer id) {
        return typeRepository.findAccountTypeById(id);
    }

    public void deleteAccountById(Integer accountTypeId) {
        typeRepository.deleteById(accountTypeId);
    }
}
