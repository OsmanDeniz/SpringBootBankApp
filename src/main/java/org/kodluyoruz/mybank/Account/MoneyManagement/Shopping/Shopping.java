package org.kodluyoruz.mybank.Account.MoneyManagement.Shopping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.kodluyoruz.mybank.Account.Card.BaseCard;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shopping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull("Urun adi bos olamaz")
    private String product;
    @NotNull("Urun tutari bos olamaz")
    private double price;
    @NotNull("Para birimi bos olamaz")
    private String currency;
    private LocalDateTime productDateTime;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "shopping_card_list",
            joinColumns = @JoinColumn(name = "shopping_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cardNumber", referencedColumnName = "cardNumber"))
    private Set<BaseCard> cardId = new HashSet<>();


    public ShoppingDto toShoppingDto() {
        return ShoppingDto.builder()
                .id(this.id)
                .product(this.product)
                .price(this.price)
                .cardId(this.cardId)
                .currency(this.currency)
                .productDateTime(this.productDateTime).build();
    }
}
