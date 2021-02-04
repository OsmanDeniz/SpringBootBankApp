package org.kodluyoruz.mybank.Account.MoneyManagement.Shopping;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShoppingService {
    private final ShoppingRepository shoppingRepository;

    public Shopping create(Shopping shopping) {
        return shoppingRepository.save(shopping);
    }
}
