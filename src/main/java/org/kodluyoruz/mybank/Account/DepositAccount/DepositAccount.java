package org.kodluyoruz.mybank.Account.DepositAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.kodluyoruz.mybank.Account.Type.AccountType;

import javax.persistence.*;
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

    private LocalDateTime created_at = LocalDateTime.now();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;

    private boolean status = true;


    public DepositAccountDto toDepositAccountDto() {


        return DepositAccountDto.builder()
                .iban(this.iban)
                .currency(this.currency)
                .balance(this.balance)
                .created_at(this.created_at)
                .accountType(this.accountType)
                .status(this.status).build();
    }

}
