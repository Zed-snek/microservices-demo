package com.keymarket.product_service.dto;

import com.keymarket.product_service.entity.ProductType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class NewProductDto {

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    private float price;

    private List<String> images;

    private int amount;

}
