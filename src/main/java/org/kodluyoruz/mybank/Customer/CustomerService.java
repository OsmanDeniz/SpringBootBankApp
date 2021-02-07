package org.kodluyoruz.mybank.Customer;

import lombok.AllArgsConstructor;
import org.kodluyoruz.mybank.Account.Card.CreditCard.CreditCardRepository;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccountDto;
import org.kodluyoruz.mybank.Account.CheckingAccount.CheckingAccountService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CheckingAccountService checkingAccountService;
    private final CreditCardRepository cardRepository;

    public Customer create(Customer customer) {
        return this.repository.save(customer);
    }

    public void update(Customer customer) {
        repository.save(customer);
    }

    public Customer findCustomerByTcKimlikNo(String identityNo) {
        return repository.findCustomerByTcKimlikNo(identityNo);
    }

    public void deleteCustomerById(Integer customerId) {
        repository.deleteById(customerId);
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

    public boolean existsByTcKimlikNo(String kimlikNo) {
        return repository.existsByTcKimlikNo(kimlikNo);
    }
}
