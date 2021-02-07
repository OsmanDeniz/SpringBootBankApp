package org.kodluyoruz.mybank.Account.CheckingAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.kodluyoruz.mybank.Account.AccountType.AccountType;
import org.kodluyoruz.mybank.Account.Card.CashCard.CashCard;
import org.kodluyoruz.mybank.Customer.Customer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckingAccount {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String iban;
    private String currency = "TRY";
    private double balance;
    private LocalDateTime created_at = LocalDateTime.now();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Customer.class)
    @JoinColumn(name = "customerId")
    private Customer customerId;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cash_card_number")
    private CashCard cashCard;

    private boolean status = true;

    public CheckingAccountDto toCheckingAccountDto() {
        return CheckingAccountDto.builder()
                .iban(this.iban)
                .customerId(this.customerId)
                .currency(this.currency)
                .balance(this.balance)
                .created_at(this.created_at)
                .accountType(this.accountType)
                .cashCard(this.cashCard)
                .status(this.status).build();
    }
}
