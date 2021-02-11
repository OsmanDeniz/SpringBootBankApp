package org.kodluyoruz.mybank.Account.MoneyManagement.Shopping;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShoppingService {
    private final ShoppingRepository shoppingRepository;

    public Shopping create(Shopping shopping) {
        return shoppingRepository.save(shopping);
    }

    public List<Shopping> findByCardId_cardNumber(String cardNumber) {
        return shoppingRepository.findByCardId_cardNumber(cardNumber);
    }
}
