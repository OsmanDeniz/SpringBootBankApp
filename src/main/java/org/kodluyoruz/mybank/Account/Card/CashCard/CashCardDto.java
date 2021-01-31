package org.kodluyoruz.mybank.Account.Card.CashCard;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Builder
@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class CashCardDto {
    private Random rnd = new Random();
    private String cardNumber;
    private String expiration_date = LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("MM/YY"));
    private String ccv = String.valueOf(rnd.nextInt(899) + 100);


    public CashCard toCashCard() {
        return CashCard.builder()
                .cardNumber(this.cardNumber)
                .expiration_date(this.expiration_date)
                .ccv(this.ccv).build();
    }
}
