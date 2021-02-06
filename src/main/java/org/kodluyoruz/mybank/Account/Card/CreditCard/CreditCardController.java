package org.kodluyoruz.mybank.Account.Card.CreditCard;

import org.kodluyoruz.mybank.Customer.Customer;
import org.kodluyoruz.mybank.Customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/customer/{id}/creditcard")

public class CreditCardController {
    private final CreditCardService cardService;
    private final CustomerService customerService;

    public CreditCardController(CreditCardService cardService, CustomerService customerService) {
        this.cardService = cardService;
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCardDto create(@PathVariable("id") int customer_id, @Valid @RequestBody CreditCardDto cardDto) throws Exception {
        if (!customerService.isCustomerExists(customer_id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id'ye ait musteri bulunamadi");

        Customer c = customerService.findById(customer_id);
        cardDto.setCreditCardCustomer(c);

        return cardService.create(cardDto.toCreditCard()).toCreditCardDto();

    }

}
