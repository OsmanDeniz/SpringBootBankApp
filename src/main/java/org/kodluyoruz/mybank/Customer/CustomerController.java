package org.kodluyoruz.mybank.Customer;


import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService service) {
        this.customerService = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto create(@Valid @RequestBody CustomerDto customerDto) {
        return customerService.create(customerDto.toCustomer()).toCustomerDto();
    }

    @GetMapping("/getAccountInfo")
    public CheckingAccountDto getInformation(@RequestParam(required = false) String checkingiban, @RequestParam(required = false) String checkingCardNumber) {

        if (checkingiban != null)
            return customerService.findCustomerByCheckingAccount_Iban(checkingiban);
        if (checkingCardNumber != null)
            return customerService.findCustomerByCheckingAccount_CardNumber(checkingCardNumber);

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gecersiz istek");
    }


}
