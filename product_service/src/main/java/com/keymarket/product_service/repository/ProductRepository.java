package com.keymarket.product_service.repository;

import com.keymarket.product_service.entity.Product;
import com.keymarket.product_service.entity.ProductType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByType(ProductType type, Pageable pageable);

}
