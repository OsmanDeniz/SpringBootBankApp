package org.kodluyoruz.mybank.Customer.Address;


import lombok.*;
import org.springframework.validation.annotation.Validated;

@Builder
@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private int id;
    private String city;
    private String province;
    private String street;
    private String number;

    public Address toAddress() {
        return Address.builder()
                .id(this.id)
                .city(this.city)
                .province(this.province)
                .street(this.street)
                .number(this.number)
                .build();

    }
}
