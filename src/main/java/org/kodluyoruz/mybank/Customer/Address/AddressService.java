package org.kodluyoruz.mybank.Customer.Address;

import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public Address create(Address address) {
        return this.repository.save(address);
    }

}
