package org.kodluyoruz.mybank.Customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccount;
import org.kodluyoruz.mybank.Account.DepositAccount.DepositAccount;
import org.kodluyoruz.mybank.Customer.Address.Address;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tcKimlikNo;
    private String name;
    private String surname;
    private String phoneNumber;
    private String gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address_id;


    @OneToMany(mappedBy = "customer_id", targetEntity = CheckingAccount.class)
    @JsonIgnore
    private Set<CheckingAccount> checkingAccount;

    @OneToMany(mappedBy = "customer_id", targetEntity = DepositAccount.class)
    @JsonIgnore
    private Set<DepositAccount> depositAccount;

    public CustomerDto toCustomerDto() {
        return CustomerDto.builder()
                .id(this.id)
                .name(this.name)
                .surname(this.surname)
                .phoneNumber(this.phoneNumber)
                .gender(this.gender)
                .tcKimlikNo(this.tcKimlikNo)
                .address_id(this.address_id)
                .build();
    }

}
