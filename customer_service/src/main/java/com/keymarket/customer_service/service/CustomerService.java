package com.keymarket.customer_service.service;

import com.keymarket.customer_service.dto.*;
import com.keymarket.customer_service.entity.Coupon;
import com.keymarket.customer_service.entity.Customer;
import com.keymarket.customer_service.exception.UserAlreadyExistsException;
import com.keymarket.customer_service.messageBroker.producer.CustomerProducer;
import com.keymarket.customer_service.repository.CouponRepository;
import com.keymarket.customer_service.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;
    private final CustomerProducer customerProducer;


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


    public float getBalanceOfCustomer(String email) {
        var customer = findByEmail(email);
        return customer.getBalance();
    }


    public void processPaymentOfOrder(PaymentRequestDto dto) {

        var customer = findById(dto.getCostumerId());

        var balance = customer.getBalance();
        var requestBuilder = SuccessfulPaymentDto.builder()
                .orderId(dto.getOrderId())
                .customerId(dto.getCostumerId());

        if (dto.getPrice() > 500) {
            var coupon = Coupon.builder()
                    .customer(customer)
                    .discountPercentage(5)
                    .maxPrice(1000)
                    .build();
            couponRepository.save(coupon);
        }

        if (balance < dto.getPrice()) {
            customer.setBalance(0F);
            requestBuilder.paidFromBalance(balance)
                    .paidFromOtherSource(dto.getPrice() - balance);
        }
        else {
            customer.setBalance(balance - dto.getPrice());
            requestBuilder.paidFromBalance(dto.getPrice());
        }
        customerRepository.save(customer);

        customerProducer.sendSuccessfulPaymentToOrderService(requestBuilder.build());
    }

    public List<CouponDto> getCouponsByCustomerId(Long customerId) {
        var customer = findById(customerId);
        var list = couponRepository.findByCustomer(customer);
        return list.stream()
                .map(coupon -> CouponDto.builder()
                        .id(coupon.getId())
                        .maxPrice(coupon.getMaxPrice())
                        .discountPercentage(coupon.getDiscountPercentage())
                        .build())
                .toList();
    }



    private Customer findByEmail(String email) {
        return customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User doesn't exist"));
    }

    private Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User doesn't exist"));
    }


    private void throwErrIfAlreadyExists(String username, String email) {
        if (customerRepository.existsCustomerByEmail(email))
            throw new UserAlreadyExistsException("User with email \"" + email +" \" already exists");
        if (customerRepository.existsCustomerByUsername(username))
            throw new UserAlreadyExistsException("User with username \"" + username +" \" already exists");
    }

}
