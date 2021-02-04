package org.kodluyoruz.mybank.Account.DepositAccount;

import lombok.*;
import org.kodluyoruz.mybank.Account.AccountType.AccountType;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class DepositAccountDto {
    private String iban;
    private String currency = "TRY";
    private double balance;

    private LocalDateTime created_at = LocalDateTime.now();
    private AccountType accountType;
    private boolean status = true;

    public DepositAccount toDepositAccount() {
        return DepositAccount.builder()
                .iban(this.iban)
                .currency(this.currency)
                .balance(this.balance)
                .created_at(this.created_at)
                .accountType(this.accountType)
                .status(this.status).build();
    }

}
