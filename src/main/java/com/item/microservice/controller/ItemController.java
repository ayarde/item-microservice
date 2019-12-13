package com.item.microservice.controller;

import com.item.microservice.model.Item;
import com.item.microservice.model.Product;
import com.item.microservice.model.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    //@Qualifier("serviceRestTemplate")
    @Qualifier("serviceFeign")
    private ItemService itemService;

    @GetMapping("/list")
    public List<Item> list(){
        return itemService.findAll();
    }

    @HystrixCommand(fallbackMethod = "alternativeMethod")
    @GetMapping("/show/{id}/quantity/{quantity}")
    public Item show(@PathVariable Long id, @PathVariable Integer quantity){
        return itemService.findById(id, quantity);
    }

    public Item alternativeMethod(Long id, Integer quantity){
        Item item = new Item();
        Product product = new Product();

        item.setQuantity(quantity);
        product.setId(id);
        product.setName("Nintendo Switch");
        product.setPrice(600.00);
        item.setProduct(product);
        return item;
    }

}
