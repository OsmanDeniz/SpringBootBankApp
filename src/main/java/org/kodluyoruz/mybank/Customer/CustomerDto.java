package org.kodluyoruz.mybank.Customer;

import lombok.*;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccount;
import org.kodluyoruz.mybank.Address.Address;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private int id;
    private String tcKimlikNo;
    @NotBlank(message = "Isim alani zorunludur.")
    private String name;
    @NotBlank(message = "Soyisim alani zorunludur.")
    private String surname;
    @NotBlank(message = "Telefon numarasi alani zorunludur.")
    private String phoneNumber;
    @NotBlank(message = "Cinsiyet alani zorunludur.")
    private String gender;
    private Address address_id;
    private CheckingAccount checkingAccount;

    public Customer toCustomer() {
        return Customer.builder()
                .id(this.id)
                .name(this.name)
                .surname(this.surname)
                .phoneNumber(this.phoneNumber)
                .gender(this.gender)
                .tcKimlikNo(this.tcKimlikNo)
                .address_id(address_id)
                .checkingAccount(this.checkingAccount)
                .build();
    }
}
