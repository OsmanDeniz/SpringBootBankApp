package org.kodluyoruz.mybank.Customer;

import org.kodluyoruz.mybank.Customer.Address.Address;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer create(Customer customer) {
        return this.repository.save(customer);
    }

    public void updateCustomer(int customerID, Address address) throws Exception {
        //TODO:Bu kisim refactor edilebilir
        Customer customer = repository.findById(customerID);
        if (customer == null) throw new Exception("Kayitli kullanici bulunamadi");
        customer.setAddress_id((Address) address);
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

    public Customer findByIban(String iban) {
        return repository.findByCheckingAccountIban(iban);
    }
}
