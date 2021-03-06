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

    public CreditCard update(CreditCard creditCard) {
        return cardRepository.save(creditCard);
    }

    public CreditCard findByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber);
    }

    public boolean isExists(String cardNumber) {
        return cardRepository.existsCreditCardByCardNumber(cardNumber);
    }

    public CreditCard findCreditCardByCreditCardCustomer_Id(Integer customerId) {
        return cardRepository.findCreditCardByCreditCardCustomer_Id(customerId);
    }

    public void deleteCreditCard(String creditCardNumber) {
        cardRepository.deleteById(creditCardNumber);
    }

}
