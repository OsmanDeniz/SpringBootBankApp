package org.kodluyoruz.mybank.Account.MoneyManagement.Shopping;

import lombok.*;
import org.kodluyoruz.mybank.Account.Card.BaseCard;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingDto {
    private int id;
    private String product;
    private double price;
    private String currency;
    private LocalDateTime productDateTime = LocalDateTime.now();
    private Set<BaseCard> cardId = new HashSet<>();

    public Shopping toShopping() {
        return Shopping.builder()
                .id(this.id)
                .product(this.product)
                .price(this.price)
                .cardId(this.cardId)
                .currency(this.currency)
                .productDateTime(this.productDateTime).build();
    }


}
