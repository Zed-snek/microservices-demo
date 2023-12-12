package com.keymarket.order_service.repository;


import com.keymarket.order_service.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

}

