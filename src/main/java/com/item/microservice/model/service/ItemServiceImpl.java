package com.item.microservice.model.service;

import com.item.microservice.model.Item;
import com.item.microservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
        List<Product> products = Arrays.asList(restClient.getForObject("http://product-microservice/list",Product[].class));
        return products.stream().map(p -> new Item(p,1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer quantity) {
        Map<String,String>  pathVariables = new HashMap<String,String>();
        pathVariables.put("id",id.toString());
        pathVariables.put("quantity", quantity.toString());
        Product product = restClient.getForObject("http://product-microservice/show/{id}",Product.class,pathVariables);
        return new Item(product,quantity);
    }

    @Override
    public Product save(Product product) {
        HttpEntity<Product> body = new HttpEntity<Product>(product);
        ResponseEntity<Product> response = restClient.exchange("http://product-microservice/create", HttpMethod.POST, body, Product.class);
        return response.getBody();
    }

    @Override
    public Product update(Product product, Long id) {
        HashMap<String,String> pathVariables = new HashMap<String, String>();
        pathVariables.put("id",id.toString());

        HttpEntity<Product> body = new HttpEntity<Product>(product);
        ResponseEntity<Product> response = restClient.exchange("http://product-microservice/edit/{id}", HttpMethod.PUT, body, Product.class, pathVariables);
        return response.getBody();
    }

    @Override
    public void delete(Long id) {
        HashMap<String,String> pathVariables = new HashMap<String, String>();
        pathVariables.put("id",id.toString());
        restClient.delete("http://product-microservice/delete/{id}",pathVariables);
    }
}
