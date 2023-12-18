package com.keymarket.order_service.repository;


import com.keymarket.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findFirst10ByCustomerId(Long customerId);

}

