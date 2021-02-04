package org.kodluyoruz.mybank.Account.Card.CreditCard;

import org.springframework.stereotype.Service;

@Service
public class CreditCardService {
    private final CreditCardRepository cardRepository;

    public CreditCardService(CreditCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public CreditCard create(CreditCard creditCard) {
        return cardRepository.save(creditCard);
    }

    public CreditCard findByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber);
    }

    public boolean isExists(String cardNumber) {
        return cardRepository.existsCreditCardByCardNumber(cardNumber);
    }
}
