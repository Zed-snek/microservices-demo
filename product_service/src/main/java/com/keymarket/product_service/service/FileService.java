package com.keymarket.product_service.service;

import com.keymarket.product_service.entity.Product;
import com.keymarket.product_service.entity.ProductImage;
import com.keymarket.product_service.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final ProductImageRepository productImageRepository;


    public void uploadImages(List<String> images, Product product) { //imagine uploading real files
        images.forEach(image -> productImageRepository.save(
                ProductImage.builder()
                        .product(product)
                        .filename(image) //imagine generating names
                        .build()
        ));
    }


    public List<String> getListOfFileNamesByProductImages(List<ProductImage> list) {
        return list.stream()
                .map(ProductImage::getFilename)
                .toList();
    }


}
