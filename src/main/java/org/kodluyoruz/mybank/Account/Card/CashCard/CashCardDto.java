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

    private String cardNumber;
    private String expiration_date = LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("MM/YY"));
    private String ccv = String.valueOf(new Random().nextInt(899) + 100);
    private String currency = "TRY";
    private Double balance;

    public CashCard toCashCard() {
        return CashCard.builder()
                .cardNumber(this.cardNumber)
                .expiration_date(this.expiration_date)
                .balance(this.balance)
                .currency(this.currency)
                .ccv(this.ccv).build();
    }
}
