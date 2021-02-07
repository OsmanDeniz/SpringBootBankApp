package org.kodluyoruz.mybank.Account.AccountType;

import lombok.*;
import org.springframework.validation.annotation.Validated;

@Builder
@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class AccountTypeDto {
    private int id;
    private AccountTypeEnum accountName;

    public AccountType toAccountType() {
        return AccountType.builder()
                .id(this.id)
                .accountName(this.accountName)
                .build();
    }
}
