package org.kodluyoruz.mybank.Account.Card.CreditCard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.kodluyoruz.mybank.Customer.Customer;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String cardNumber;
    private String expiration_date;
    private String ccv;
    private Double balance;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "creditCardUserID")
    private Customer creditCardCustomer;


    public CreditCardDto toCreditCardDto() {
        return CreditCardDto.builder()
                .cardNumber(this.cardNumber)
                .balance(this.balance)
                .expiration_date(this.expiration_date)
                .ccv(this.ccv)
                .creditCardCustomer(this.creditCardCustomer)
                .build();
    }

}
