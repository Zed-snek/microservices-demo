package com.keymarket.product_service.controller;

import com.keymarket.product_service.dto.NewProductDto;
import com.keymarket.product_service.dto.ProductDto;
import com.keymarket.product_service.entity.ProductType;
import com.keymarket.product_service.response.MySuccessResponse;
import com.keymarket.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;


    @PostMapping("")
    public ResponseEntity<MySuccessResponse> createProduct(@RequestBody NewProductDto dto) {
        productService.createProduct(dto);

        var response = new MySuccessResponse("Product is created");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<MySuccessResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        var response = new MySuccessResponse("Product is deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/reduce/{id}")
    public ResponseEntity<MySuccessResponse> reduceProductAmount(
            @PathVariable Long id,
            @RequestParam int amount
    ) {
        productService.reduceProductAmount(id, amount);

        var response = new MySuccessResponse("Product is updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/add/{id}")
    public ResponseEntity<MySuccessResponse> addProductAmount(
            @PathVariable Long id,
            @RequestParam int amount
    ) {
        productService.addProductAmount(id, amount);

        var response = new MySuccessResponse("Product is updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/get_by_type/{type}")
    public List<ProductDto> getByType(
            @PathVariable ProductType type,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "20", required = false) int size
    ) {

        return productService.getProductsByType(type, page, size);
    }


    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable Long id) {

        return productService.getProductById(id);
    }


}
