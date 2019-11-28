package com.item.microservice.model.service;

import com.item.microservice.model.Item;
import com.item.microservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("serviceRestTemplate")
public class ItemServiceImpl  implements ItemService{

    @Autowired
    private RestTemplate restClient;

    @Override
    public List<Item> findAll() {
        List<Product> products = Arrays.asList(restClient.getForObject("http://localhost:8080/list",Product[].class));
        return products.stream().map(p -> new Item(p,1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer quantity) {
        Map<String,String>  pathVariables = new HashMap<String,String>();
        pathVariables.put("id",id.toString());
        pathVariables.put("quantity", quantity.toString());
        Product product = restClient.getForObject("http://localhost:8080/show/{id}",Product.class,pathVariables);
        return new Item(product,quantity);
    }
}
