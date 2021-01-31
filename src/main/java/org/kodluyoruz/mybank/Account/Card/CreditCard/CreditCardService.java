package org.kodluyoruz.mybank.Account.Card.CreditCard;

import org.springframework.stereotype.Service;

@Service
public class CreditCardService {
    private CreditCardRepository cardRepository;

    public CreditCardService(CreditCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public CreditCard create(CreditCard creditCard) {
        return cardRepository.save(creditCard);
    }


}
