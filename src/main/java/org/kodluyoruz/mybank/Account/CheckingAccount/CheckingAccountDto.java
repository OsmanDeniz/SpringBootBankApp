package org.kodluyoruz.mybank.Account.CheckingAccount;

import lombok.*;
import org.kodluyoruz.mybank.Account.AccountType.AccountType;
import org.kodluyoruz.mybank.Account.Card.CashCard.CashCard;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class CheckingAccountDto {
    private String iban;
    private String currency = "TRY";
    private double balance = 0;

    private LocalDateTime created_at = LocalDateTime.now();
    private CashCard cashCard;

    private boolean status = true;
    private AccountType accountType;

    public CheckingAccount toCheckingDto() {
        return CheckingAccount.builder()
                .iban(this.iban)
                .currency(this.currency)
                .balance(this.balance)
                .created_at(this.created_at)
                .accountType(this.accountType)
                .cashCard(this.cashCard)
                .status(this.status).build();
    }

}
