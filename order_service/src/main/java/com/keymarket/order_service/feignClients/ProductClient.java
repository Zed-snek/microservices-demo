package com.keymarket.order_service.feignClients;

import com.keymarket.order_service.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Set;

@FeignClient(name = "productServiceClient", url = "${api.productservice}")
public interface ProductClient {

    @GetMapping(value = "/get_products")
    List<ProductDto> getProductsInfo(@RequestParam("items") Set<Long> items);

}
