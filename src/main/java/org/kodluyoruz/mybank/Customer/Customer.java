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
import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "Kimlik numarasi zorunludur")
    private String tcKimlikNo;

    @NotBlank(message = "Isim alani zorunludur")
    private String name;

    @NotBlank(message = "Soyisim alani zorunludur")
    private String surname;

    @NotBlank(message = "Telefon numarasi alani zorunludur")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @JsonIgnore
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

