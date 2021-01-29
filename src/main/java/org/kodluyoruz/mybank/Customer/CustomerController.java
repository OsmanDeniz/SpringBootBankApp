package org.kodluyoruz.mybank.Customer;


import org.kodluyoruz.mybank.Address.Address;
import org.kodluyoruz.mybank.Address.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService service) {
        this.customerService = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto create(@Valid @RequestBody CustomerDto customerDto) {
        Customer c = new Customer();
        return customerService.create(customerDto.toCustomer()).toCustomerDto();
    }




}
