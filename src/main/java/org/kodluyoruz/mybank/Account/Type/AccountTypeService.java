package org.kodluyoruz.mybank.Account.Type;

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

    public AccountType findAccountByName(String name) {
        return typeRepository.findByAccountName(name);
    }

    public boolean isAccountTypeExists(String name) {
        return typeRepository.existsByAccountName(name);
    }

}
