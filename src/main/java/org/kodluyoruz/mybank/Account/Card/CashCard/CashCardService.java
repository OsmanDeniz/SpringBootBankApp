package org.kodluyoruz.mybank.Account.Card.CashCard;

import org.springframework.stereotype.Service;

@Service
public class CashCardService {
    private CashCardRepository cardRepository;

    public CashCardService(CashCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public CashCard create(CashCard cashCard) {
        return cardRepository.save(cashCard);
    }


}
