package org.kodluyoruz.mybank.Account.CheckingAccount;

import org.springframework.data.repository.CrudRepository;

public interface CheckingAccountRepository extends CrudRepository<CheckingAccount, String> {
    CheckingAccount findByIban(String account_iban);

    CheckingAccount findByCashCardCardNumber(String cardNumber);

}
