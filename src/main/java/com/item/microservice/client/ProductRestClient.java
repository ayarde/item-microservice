package com.item.microservice.client;

import com.item.microservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-microservice")
public interface ProductRestClient {

    @GetMapping("/list")
    public List<Product> list();

    @GetMapping("/show/{id}")
    public Product show(@PathVariable Long id);
}

