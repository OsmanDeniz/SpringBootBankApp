package org.kodluyoruz.mybank.Account.Card.CashCard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashCard {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String cardNumber;
    private String expiration_date;
    private String ccv;


    public CashCardDto toCashCardDto() {
        return CashCardDto.builder()
                .cardNumber(this.cardNumber)
                .expiration_date(this.expiration_date)
                .ccv(this.ccv)
                .build();
    }

}
