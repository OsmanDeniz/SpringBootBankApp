package org.kodluyoruz.mybank.Customer.Address;

import org.kodluyoruz.mybank.Customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/customer/{id}/address")
public class AddressController {
    private final AddressService addressService;
    private final CustomerService customerService;

    public AddressController(AddressService addressService, CustomerService customerService) {
        this.addressService = addressService;
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDto create(@PathVariable("id") int customer_id, @Valid @RequestBody AddressDto addressDto) throws Exception {
        AddressDto adt = null;
        if (!customerService.isCustomerExists(customer_id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kullanici bulunamadi");
        adt = addressService.create(addressDto.toAddress()).toAddressDto();
        this.customerService.updateCustomer(customer_id, adt.toAddress());
        return adt;
    }
}
