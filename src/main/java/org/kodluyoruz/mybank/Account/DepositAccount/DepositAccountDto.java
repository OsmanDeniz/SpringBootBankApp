package org.kodluyoruz.mybank.Account.DepositAccount;

import lombok.*;
import org.kodluyoruz.mybank.Account.AccountType.AccountType;
import org.kodluyoruz.mybank.Customer.Customer;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class DepositAccountDto {
    private String iban;

    @NotBlank(message = "Para birimi zorunlu alandir.")
    private String currency = "TRY";

    @NotBlank(message = "Para miktari zorunlu alandir.")
    private double balance;

    @NotBlank(message = "Faiz oranizorunlu alandir.")
    private double interest_rate = 1.0;

    @NotBlank(message = "Kac gun yatirilmasi gerektigi zorunlu alandir.")
    private int investment_day = 1;

    private LocalDateTime created_at = LocalDateTime.now();

    private AccountType accountType;

    private Customer customerId;

    private boolean status = true;

    public DepositAccount toDepositAccount() {
        return DepositAccount.builder()
                .iban(this.iban)
                .customerId(this.customerId)
                .currency(this.currency)
                .balance(this.balance)
                .interest_rate(this.interest_rate)
                .investment_day(this.investment_day)
                .created_at(this.created_at)
                .accountType(this.accountType)
                .status(this.status).build();
    }

}
