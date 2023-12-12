package com.keymarket.product_service.dto;

import com.keymarket.product_service.entity.ProductType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ProductDto {

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    private AvailabilityOfProduct availability;

    private float price;

    private List<String> images;

}
