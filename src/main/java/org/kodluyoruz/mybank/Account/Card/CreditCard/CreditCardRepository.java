package org.kodluyoruz.mybank.Account.Card.CreditCard;

import org.springframework.data.repository.CrudRepository;

public interface CreditCardRepository extends CrudRepository<CreditCard, String> {
    CreditCard findByCardNumber(String cardNumber);

    boolean existsCreditCardByCardNumber(String cardNumber);
}
