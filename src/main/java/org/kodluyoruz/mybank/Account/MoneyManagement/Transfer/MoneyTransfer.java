package org.kodluyoruz.mybank.Account.MoneyManagement.Transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MoneyTransfer {
    @EmbeddedId
    private CompositeTransferIdentity transferIdentity;
    private String sourceCurrency;
    private String targetCurrency;
    private Double amount;
    private LocalDateTime transferDate;


    public MoneyTransferDto toMoneyTransferDto() {
        return MoneyTransferDto.builder()
                .transferIdentity(this.transferIdentity)
                .sourceCurrency(this.sourceCurrency)
                .targetCurrency(this.targetCurrency)
                .amount(this.amount)
                .transferDate(this.transferDate)
                .build();
    }
}
