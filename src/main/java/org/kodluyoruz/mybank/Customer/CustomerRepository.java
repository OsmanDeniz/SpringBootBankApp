package org.kodluyoruz.mybank.Customer;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Customer findById(int customer_id);

    Customer findByCheckingAccountIban(String iban);

}
