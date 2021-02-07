package org.kodluyoruz.mybank.Customer;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Customer findById(int customer_id);

    Customer findCustomerByTcKimlikNo(String identityNo);

    boolean existsByTcKimlikNo(String tcKimlikNo);

}
