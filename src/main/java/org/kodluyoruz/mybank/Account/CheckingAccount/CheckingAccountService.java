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



}
