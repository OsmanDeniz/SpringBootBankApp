package org.kodluyoruz.mybank.Customer;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.AllArgsConstructor;
import org.kodluyoruz.mybank.Account.Card.CreditCard.CreditCardService;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/customer")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private CheckingAccountService checkingAccountService;
    private CreditCardService creditCardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto create(@Valid @RequestBody CustomerDto customerDto) {
        if (customerService.existsByTcKimlikNo(customerDto.getTcKimlikNo()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Kimlik numarasina sahip musteri sistemde var.");

        return customerService.create(customerDto.toCustomer()).toCustomerDto();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@Valid @RequestBody CustomerDto customerDto, @RequestParam String kimlikNo) {
        Customer customer = customerService.findCustomerByTcKimlikNo(kimlikNo);
        if (customer == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Guncellenecek musteri bulunamadi");

        customerDto.setAddress_id(customer.getAddress_id());
        customerDto.setId(customer.getId());
        customerService.update(customerDto.toCustomer());
    }

    @GetMapping
    public Customer findCustomerByTcKimlikNo(@RequestParam String kimlikNo) {
        Customer customer = customerService.findCustomerByTcKimlikNo(kimlikNo);
        if (customer == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kimlik numarasina ait musteri bulunamadi");
        return customer;
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.OK, reason = "Musteri basariyla silindi.")
    public void deleteCustomer(@RequestParam String customerIdentityNumber) {
        Customer deleteCustomer = customerService.findCustomerByTcKimlikNo(customerIdentityNumber);
        if (deleteCustomer == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kimlik numarasina ait sistemde kayit bulunamadi.");


        if (checkingAccountService.findCheckingAccountByCustomer_id(deleteCustomer.getId()) != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Musteriye ait vadesiz hesap bulunuyor, once hesap silinmelidir.");

        if (creditCardService.findCreditCardByCreditCardCustomer_Id(deleteCustomer.getId()) != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Musteriye ait kredi karti bulunuyor, once kart silinmelidir.");

        customerService.deleteCustomerById(deleteCustomer.getId());
    }

    @RestControllerAdvice
    public class GlobalControllerExceptionHandler {
        @ExceptionHandler({InvalidFormatException.class, ConstraintViolationException.class})
        public ResponseEntity<String> handleConflict(RuntimeException ex) {
            return new ResponseEntity<>("Parametreleri kontrol edin lutfen, mesaj icerigi: \n\n" + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/getAccountInfo")
//    public CheckingAccountDto getInformation(@RequestParam(required = false) String checkingiban, @RequestParam(required = false) String checkingCardNumber) {
//
//        if (checkingiban != null)
//            return customerService.findCustomerByCheckingAccount_Iban(checkingiban);
//        if (checkingCardNumber != null)
//            return customerService.findCustomerByCheckingAccount_CardNumber(checkingCardNumber);
//
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gecersiz istek");
//    }


}
