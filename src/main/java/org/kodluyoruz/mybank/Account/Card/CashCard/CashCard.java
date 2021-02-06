package org.kodluyoruz.mybank.Account.Card.CashCard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kodluyoruz.mybank.Account.Card.BaseCard;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashCard extends BaseCard {
    @Id
    protected String cardNumber;
    protected String expiration_date;
    protected String ccv;
    protected String currency;


    public CashCardDto toCashCardDto() {
        return CashCardDto.builder()
                .cardNumber(this.cardNumber)
                .expiration_date(this.expiration_date)
                .ccv(this.ccv)
                .currency(this.currency)
                .build();
    }

}
