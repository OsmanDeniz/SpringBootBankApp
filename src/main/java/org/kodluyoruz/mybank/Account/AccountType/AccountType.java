package org.kodluyoruz.mybank.Account.AccountType;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private AccountTypeEnum accountName;

    public AccountTypeDto toAccountTypeDto() {
        return AccountTypeDto.builder()
                .id(this.id)
                .accountName(this.accountName).build();
    }
}
