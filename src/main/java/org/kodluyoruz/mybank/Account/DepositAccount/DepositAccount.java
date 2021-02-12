package org.kodluyoruz.mybank.Account.DepositAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.kodluyoruz.mybank.Account.AccountType.AccountType;
import org.kodluyoruz.mybank.Customer.Customer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositAccount {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String iban;
    private String currency = "TRY";
    private double balance;
    private double interest_rate;
    private int investment_day;
    private LocalDateTime created_at = LocalDateTime.now();
    private boolean status = true;

    @OneToOne
    @JoinColumn(name = "account_type_id")
    @NotNull
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customerId;

    public DepositAccountDto toDepositAccountDto() {


        return DepositAccountDto.builder()
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
