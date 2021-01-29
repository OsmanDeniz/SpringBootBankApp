package org.kodluyoruz.mybank.Account.Type;

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
    private String accountName;


    public AccountType toAccountType() {
        return AccountType.builder()
                .id(this.id)
                .accountName(this.accountName)
                .build();
    }
}
