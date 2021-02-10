package org.kodluyoruz.mybank.Account.CheckingAccount;

import org.springframework.stereotype.Service;

@Service
public class CheckingAccountService {
    private final CheckingAccountRepository checkingAccountRepository;

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

    public CheckingAccount update(CheckingAccount checkingAccount) {
        return checkingAccountRepository.save(checkingAccount);
    }

    public CheckingAccount findByCashCardCardNumber(String cardNumber) {
        return checkingAccountRepository.findByCashCardCardNumber(cardNumber);
    }

    public CheckingAccount findCheckingAccountByCustomer_id(Integer customer_id) {
        return checkingAccountRepository.findByCustomerId_Id(customer_id);
    }

    public void deleteById(String iban) {
        checkingAccountRepository.deleteById(iban);
    }

}
