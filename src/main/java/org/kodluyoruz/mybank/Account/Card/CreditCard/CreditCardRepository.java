package org.kodluyoruz.mybank.Account.Card.CreditCard;

import org.springframework.data.repository.CrudRepository;

public interface CreditCardRepository extends CrudRepository<CreditCard, String> {
    CreditCard findByCardNumber(String cardNumber);

    CreditCard findCreditCardByCardNumber(String cardNumber);

    boolean existsCreditCardByCardNumber(String cardNumber);

    CreditCard findCreditCardByCreditCardCustomer_Id(Integer customerId);

    void deleteByCardNumber(CreditCard creditCard);

}
