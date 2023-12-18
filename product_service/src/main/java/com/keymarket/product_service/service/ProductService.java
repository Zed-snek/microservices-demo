package com.keymarket.product_service.service;


import com.keymarket.product_service.dto.*;
import com.keymarket.product_service.entity.Product;
import com.keymarket.product_service.entity.ProductType;
import com.keymarket.product_service.exception.BusinessLogicException;
import com.keymarket.product_service.messageBroker.produce.ProductProducer;
import com.keymarket.product_service.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final FileService fileService;


    private final ProductProducer productProducer;


    @Transactional
    public void createProduct(NewProductDto dto) {
        var product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .type(dto.getType())
                .amount(dto.getAmount())
                .build();

        productRepository.save(product);

        fileService.uploadImages(dto.getImages(), product);
    }


    public void deleteProduct(Long id) {
        var product = findById(id);
        if (product.getAmount() > 0)
            throw new BusinessLogicException("Product is not empty to be deleted");

        productRepository.delete(product);
    }


    public void reduceProductAmount(Long idProduct, int howMany) {
        var product = findById(idProduct);
        if (product.getAmount() < howMany)
            throw new BusinessLogicException("There is not so many products available on inventory");

        product.setAmount(product.getAmount() - howMany);
        productRepository.save(product);
    }


    public void addProductAmount(Long idProduct, int howMany) {
        var product = findById(idProduct);

        product.setAmount(product.getAmount() + howMany);
        productRepository.save(product);
    }


    public List<ProductDto> getProductsByType(ProductType type, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var list = productRepository.findByType(type, pageable);

        return list.stream()
                .map(this::mapProductToDto)
                .toList();
    }


    public ProductDto getProductById(Long id) {
        var product = findById(id);
        return mapProductToDto(product);
    }


    public List<ProductItemDto> getProductsByIds(Iterable<Long> ids) {
        var products = productRepository.findAllById(ids);

        return products.stream()
                .map(product -> ProductItemDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .image(product.getImages().get(0).getFilename())
                        .build())
                .toList();
    }


    @Transactional
    public void sumPriceAndCheckAvailabilityOfProducts(NewOrderDto dto) {

        var items = dto.getItems();
        var products = productRepository.findAllById(items.keySet());
        float sum = products.stream()
                .peek(p -> {
                    int requestedAmount = items.get(p.getId());
                    if (p.getAmount() < requestedAmount)
                        throw new BusinessLogicException("Not enough keys on inventory");
                    p.setAmount(p.getAmount() - requestedAmount);
                    productRepository.save(p);
                })
                .map(p -> items.get(p.getId()) * p.getPrice())
                .reduce(0F, Float::sum);

        var toSend = OrderConfirmationPriceDto.builder()
                .orderId(dto.getOrderId())
                .price(sum)
                .build();
        productProducer.sendOrderConfirmationToOrderService(toSend);
    }


    private ProductDto mapProductToDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .type(product.getType())
                .price(product.getPrice())
                .availability(checkAvailabilityOfProduct(product.getAmount()))
                .images(fileService.getListOfFileNamesByProductImages(product.getImages()))
                .build();
    }


    private AvailabilityOfProduct checkAvailabilityOfProduct(int amount) {
        if (amount > 100)
            return AvailabilityOfProduct.HIGH;
        if (amount > 30)
            return AvailabilityOfProduct.MEDIUM;
        if (amount > 0)
            return AvailabilityOfProduct.LOW;

        return AvailabilityOfProduct.UNAVAILABLE;
    }


    private Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product is not found"));
    }

}
