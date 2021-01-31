package org.kodluyoruz.mybank.Account.CheckingAccount;

import org.springframework.stereotype.Service;

@Service
public class CheckingAccountService {
    private CheckingAccountRepository checkingAccountRepository;

    public CheckingAccountService(CheckingAccountRepository checkingAccountRepository) {
        this.checkingAccountRepository = checkingAccountRepository;
    }

    public CheckingAccount create(CheckingAccount checkingAccount) {
        return checkingAccountRepository.save(checkingAccount);
    }

    public CheckingAccount findByIban(String account_iban) {
        return checkingAccountRepository.findByIban(account_iban);
    }

    public boolean isAccountExists(String iban) {
        return checkingAccountRepository.existsById(iban);
    }


    public void update(CheckingAccount checkingAccount) {
        checkingAccountRepository.save(checkingAccount);
    }
}
