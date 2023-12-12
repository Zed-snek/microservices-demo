package com.keymarket.customer_service.repository;

import com.keymarket.customer_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsCustomerByEmail(String email);
    boolean existsCustomerByUsername(String username);

    Optional<Customer> findCustomerByEmail(String email);
}
