package com.item.microservice.client;

import com.microservice.common.model.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-microservice")
public interface ProductRestClient {

    @GetMapping("/list")
    public List<Product> list();

    @GetMapping("/show/{id}")
    public Product show(@PathVariable Long id);

    @PostMapping("/create")
    public Product create(@RequestBody Product product);

    @PutMapping("/edit/{id}")
    public Product edit(@RequestBody Product product, @PathVariable Long id);

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id);
}

