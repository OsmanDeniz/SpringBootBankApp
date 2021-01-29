package org.kodluyoruz.mybank.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccount;
import org.kodluyoruz.mybank.Address.Address;

import javax.persistence.*;

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


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "iban_checking_account")
    private CheckingAccount checkingAccount;

    public CustomerDto toCustomerDto() {
        return CustomerDto.builder()
                .id(this.id)
                .name(this.name)
                .surname(this.surname)
                .phoneNumber(this.phoneNumber)
                .gender(this.gender)
                .tcKimlikNo(this.tcKimlikNo)
                .address_id(this.address_id)
                .checkingAccount(this.checkingAccount)
                .build();
    }

}
