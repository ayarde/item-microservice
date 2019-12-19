package com.item.microservice.model.service;

import com.item.microservice.client.ProductRestClient;
import com.item.microservice.model.Item;
import com.microservice.common.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("serviceFeign")
public class ItemServiceFeign implements  ItemService{

    @Autowired
    private ProductRestClient productRestClient;

    @Override
    public List<Item> findAll() {
        return productRestClient.list().stream().map(p -> new Item(p,1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer quantity) {
        return new Item(productRestClient.show(id),quantity);
    }

    @Override
    public Product save(Product product) {
        return productRestClient.create(product);
    }

    @Override
    public Product update(Product product, Long id) {
        return productRestClient.edit(product,id);
    }

    @Override
    public void delete(Long id) {
        productRestClient.delete(id);
    }
}
