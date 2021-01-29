package org.kodluyoruz.mybank.Address;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kodluyoruz.mybank.Customer.Customer;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String city;
    private String province;
    private String street;
    private String number;


    public AddressDto toAddressDto() {
        return AddressDto.builder()
                .id(this.id)
                .city(this.city)
                .province(this.province)
                .street(this.street)
                .number(this.number)
                .build();
    }
}
