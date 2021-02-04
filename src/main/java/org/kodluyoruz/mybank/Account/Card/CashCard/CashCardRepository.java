package org.kodluyoruz.mybank.Account.Card.CashCard;

import org.springframework.data.repository.CrudRepository;

public interface CashCardRepository extends CrudRepository<CashCard, String> {
    CashCard findByCardNumber(String cardNumber);

    boolean existsCashCardByCardNumber(String cardNumber);
}
