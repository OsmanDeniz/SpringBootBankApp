package org.kodluyoruz.mybank.Account.MoneyManagement.Transfer;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class MoneyTransferDto {
    private CompositeTransferIdentity transferIdentity;
    private String sourceCurrency;
    private String targetCurrency;
    private Double amount;
    private LocalDateTime transferDate = LocalDateTime.now();

    public MoneyTransfer toMoneyTransfer() {
        return MoneyTransfer.builder()
                .transferIdentity(this.transferIdentity)
                .sourceCurrency(this.sourceCurrency)
                .targetCurrency(this.targetCurrency)
                .amount(this.amount)
                .transferDate(this.transferDate)
                .build();
    }


}
