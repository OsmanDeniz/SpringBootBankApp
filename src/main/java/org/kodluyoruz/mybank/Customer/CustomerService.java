package org.kodluyoruz.mybank.Customer;

import lombok.AllArgsConstructor;
import org.kodluyoruz.mybank.Account.Card.CreditCard.CreditCardRepository;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccountDto;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccountService;
import org.kodluyoruz.mybank.Customer.Address.Address;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CheckingAccountService checkingAccountService;
    private final CreditCardRepository cardRepository;

    public Customer create(Customer customer) {
        return this.repository.save(customer);
    }

    public void updateCustomer(int customerID, Address address) throws Exception {
        Customer customer = repository.findById(customerID);
        if (customer == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Kayitli kullanici bulunamadi");
        customer.setAddress_id(address);
        repository.save(customer);
    }

    public void update(Customer customer) {
        repository.save(customer);
    }

    public boolean isCustomerExists(int customerid) {
        return repository.existsById(customerid);
    }

    public Customer findById(int customer_id) {
        return repository.findById(customer_id);
    }

    public CheckingAccountDto findCustomerByCheckingAccount_Iban(String iban) {
        return checkingAccountService.findByIban(iban).toCheckingAccountDto();
    }

    public CheckingAccountDto findCustomerByCheckingAccount_CardNumber(String cardNumber) {
        return checkingAccountService.findByCashCardCardNumber(cardNumber).toCheckingAccountDto();
    }
}
