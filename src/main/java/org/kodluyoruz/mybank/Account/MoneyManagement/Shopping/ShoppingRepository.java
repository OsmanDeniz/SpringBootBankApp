package org.kodluyoruz.mybank.Account.MoneyManagement.Shopping;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShoppingRepository extends CrudRepository<Shopping, Integer> {
    List<Shopping> findByCardId_cardNumber(String cardNumber);

//    List<BaseCard> findById_cardNumber(int cardNumber);
}
