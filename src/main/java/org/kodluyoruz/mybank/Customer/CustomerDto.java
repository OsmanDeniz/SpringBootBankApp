package org.kodluyoruz.mybank.Customer;

import lombok.*;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccount;
import org.kodluyoruz.mybank.Account.DepositAccount.DepositAccount;
import org.kodluyoruz.mybank.Customer.Address.Address;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Builder
@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private int id;
    private String tcKimlikNo;
    private String name;
    private String surname;
    private String phoneNumber;
    private Gender gender;
    private Address address_id;
    private Set<CheckingAccount> checkingAccount;
    private Set<DepositAccount> depositAccount;

    public Customer toCustomer() {
        return Customer.builder()
                .id(this.id)
                .name(this.name)
                .surname(this.surname)
                .phoneNumber(this.phoneNumber)
                .gender(this.gender)
                .tcKimlikNo(this.tcKimlikNo)
                .address_id(address_id)
                .build();
    }
}
