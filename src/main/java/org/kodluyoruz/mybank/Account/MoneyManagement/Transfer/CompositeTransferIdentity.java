package org.kodluyoruz.mybank.Account.MoneyManagement.Transfer;


import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompositeTransferIdentity implements Serializable {
    @NotNull
    private String sourceIban;
    @NotNull
    private String targetIban;
}
