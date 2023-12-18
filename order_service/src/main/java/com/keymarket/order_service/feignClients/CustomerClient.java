package com.keymarket.order_service.feignClients;

import com.keymarket.order_service.dto.CouponDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "customerServiceClient", url = "${api.customerservice}")
public interface CustomerClient {

    @GetMapping(value = "/coupon/{customerId}")
    List<CouponDto> getProductsInfo(@PathVariable Long customerId);

}
