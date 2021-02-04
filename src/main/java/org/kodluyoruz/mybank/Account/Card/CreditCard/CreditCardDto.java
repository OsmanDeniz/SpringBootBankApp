package org.kodluyoruz.mybank.Account.Card.CreditCard;

import lombok.*;
import org.kodluyoruz.mybank.Customer.Customer;
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
public class CreditCardDto {
    @ToString.Exclude
    private Random rnd = new Random();
    private String cardNumber;
    private String expiration_date = LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("MM/YY"));
    private String ccv = String.valueOf(rnd.nextInt(899) + 100);
    private Double balance;
    private Customer creditCardCustomer;
    private String currency = "TRY";

    public CreditCard toCreditCard() {
        return CreditCard.builder()
                .cardNumber(this.cardNumber)
                .balance(this.balance)
                .expiration_date(this.expiration_date)
                .ccv(this.ccv)
                .currency(this.currency)
                .creditCardCustomer(this.creditCardCustomer)
                .build();
    }
}
