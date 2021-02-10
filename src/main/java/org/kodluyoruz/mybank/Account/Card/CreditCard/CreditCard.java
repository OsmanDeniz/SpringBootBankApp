package org.kodluyoruz.mybank.Account.Card.CreditCard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kodluyoruz.mybank.Account.Card.BaseCard;
import org.kodluyoruz.mybank.Customer.Customer;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard extends BaseCard {
    @Id
    protected String cardNumber;
    protected String expiration_date;
    protected String ccv;
    protected Double balance;
    protected Double debt;
    protected String currency;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "creditCardUserID")
    private Customer creditCardCustomer;


    public CreditCardDto toCreditCardDto() {
        return CreditCardDto.builder()
                .cardNumber(this.cardNumber)
                .balance(this.balance)
                .debt(this.debt)
                .expiration_date(this.expiration_date)
                .ccv(this.ccv)
                .currency(this.currency)
                .creditCardCustomer(this.creditCardCustomer)
                .build();
    }

}
