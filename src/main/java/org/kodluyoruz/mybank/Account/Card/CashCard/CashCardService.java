package org.kodluyoruz.mybank.Account.Card.CashCard;

import org.springframework.stereotype.Service;

@Service
public class CashCardService {
    private final CashCardRepository cardRepository;

    public CashCardService(CashCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public CashCard create(CashCard cashCard) {
        return cardRepository.save(cashCard);
    }

    public CashCard findByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber);
    }

    public boolean isExists(String cardNumber) {
        return cardRepository.existsCashCardByCardNumber(cardNumber);
    }

}
