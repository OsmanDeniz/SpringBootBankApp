package org.kodluyoruz.mybank.Customer.Address;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.AllArgsConstructor;
import org.kodluyoruz.mybank.Customer.Customer;
import org.kodluyoruz.mybank.Customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/customer/{id}/address")
@AllArgsConstructor
public class AddressController {
    private final AddressService addressService;
    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDto create(@PathVariable("id") int customer_id, @Valid @RequestBody AddressDto addressDto) throws Exception {
        AddressDto adt = null;
        Customer customer = customerService.findById(customer_id);
        if (customer == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kullanici bulunamadi");

        adt = addressService.create(addressDto.toAddress()).toAddressDto();

        if (customer.getAddress_id() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kullaniciya ait adres var");

        customer.setAddress_id(adt.toAddress());
        customerService.update(customer);
        return adt;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Address findAdressByCustomerId(@PathVariable(name = "id") Integer customerId) {

        Customer customer = customerService.findById(customerId);
        if (customer == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Musteri bulunamadi");

        Address address = customer.getAddress_id();
        if (address == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Musteri adresi bulunamadi.");

        return address;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Address updateAddress(@PathVariable("id") Integer customerId, @RequestBody AddressDto addressDto) {

        if (addressDto == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adres bilgileri bos olamaz.");

        Customer customer = customerService.findById(customerId);
        if (customer == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Musteri bulunamadi");

        Address address = customer.getAddress_id();
        if (address == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Musteri adresi bulunamadi.");

        addressDto.setId(address.getId());

        addressService.update(addressDto.toAddress());

        return addressDto.toAddress();
    }

    @RestControllerAdvice
    public class GlobalControllerExceptionHandler {
        @ExceptionHandler({InvalidFormatException.class, ConstraintViolationException.class})
        public ResponseEntity<String> handleConflict(RuntimeException ex) {
            return new ResponseEntity<>("Parametreleri kontrol edin lutfen, mesaj icerigi: \n\n" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
