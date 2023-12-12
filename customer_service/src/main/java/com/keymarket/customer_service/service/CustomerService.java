package com.keymarket.customer_service.service;

import com.keymarket.customer_service.dto.CustomerDto;
import com.keymarket.customer_service.dto.NewCustomerDto;
import com.keymarket.customer_service.dto.BalanceDto;
import com.keymarket.customer_service.entity.Customer;
import com.keymarket.customer_service.exception.BusinessLogicException;
import com.keymarket.customer_service.exception.UserAlreadyExistsException;
import com.keymarket.customer_service.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;


    public void registerCustomer(NewCustomerDto dto) {
        throwErrIfAlreadyExists(dto.getUsername(), dto.getEmail());

        var newCustomer = Customer.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
        customerRepository.save(newCustomer);
    }


    public CustomerDto getCustomerInfo(String email) {
        var customer = findByEmail(email);

        return CustomerDto.builder()
                .username(customer.getUsername())
                .balance(customer.getBalance())
                .build();
    }



    public void topUpBalance(BalanceDto dto) {
        var customer = findByEmail(dto.getEmail());
        customer.setBalance(customer.getBalance() + dto.getMoney());
        customerRepository.save(customer);
    }


    public void withdrawFromBalance(BalanceDto dto) {
        var customer = findByEmail(dto.getEmail());
        if (customer.getBalance() < dto.getMoney())
            throw new BusinessLogicException("Not enough money on the balance");

        customer.setBalance(customer.getBalance() - dto.getMoney());
        customerRepository.save(customer);
    }


    public float getBalanceOfCustomer(String email) {
        var customer = findByEmail(email);
        return customer.getBalance();
    }


    private Customer findByEmail(String email) {
        return customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User doesn't exist"));
    }


    private void throwErrIfAlreadyExists(String username, String email) {
        if (customerRepository.existsCustomerByEmail(email))
            throw new UserAlreadyExistsException("User with email \"" + email +" \" already exists");
        if (customerRepository.existsCustomerByUsername(username))
            throw new UserAlreadyExistsException("User with username \"" + username +" \" already exists");
    }

}
