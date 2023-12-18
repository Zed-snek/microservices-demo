package com.keymarket.customer_service.repository;

import com.keymarket.customer_service.entity.Coupon;
import com.keymarket.customer_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findByCustomer(Customer customer);

}
