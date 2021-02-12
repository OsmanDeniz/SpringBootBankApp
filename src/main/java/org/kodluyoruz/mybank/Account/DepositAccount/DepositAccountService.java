package org.kodluyoruz.mybank.Account.DepositAccount;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepositAccountService {
    private final DepositAccountRepository depositAccountRepository;

    public DepositAccount create(DepositAccount depositAccount) {
        return depositAccountRepository.save(depositAccount);
    }

    public DepositAccount findDepositAccountByIban(String accountiban) {
        return depositAccountRepository.findByIban(accountiban);
    }

    public void deleteDepositAccount(String accountIban) {
        depositAccountRepository.deleteById(accountIban);
    }
}
