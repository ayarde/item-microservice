package com.item.microservice.model.service;

import com.item.microservice.model.Item;
import com.item.microservice.model.Product;

import java.util.List;

public interface ItemService {
    public List<Item> findAll();
    public Item findById(Long id, Integer quantity);
    public Product save(Product product);
    public Product update(Product product, Long id);
    public void delete(Long id);
}
