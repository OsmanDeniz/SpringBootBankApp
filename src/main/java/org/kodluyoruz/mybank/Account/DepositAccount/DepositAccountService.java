package org.kodluyoruz.mybank.Account.DepositAccount;

import org.springframework.stereotype.Service;

@Service
public class DepositAccountService {
    private final DepositAccountRepository depositAccountRepository;

    public DepositAccountService(DepositAccountRepository depositAccountRepository) {
        this.depositAccountRepository = depositAccountRepository;
    }

    public DepositAccount create(DepositAccount depositAccount) {
        return depositAccountRepository.save(depositAccount);
    }
}
