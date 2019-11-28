package com.item.microservice.model.service;

import com.item.microservice.model.Item;

import java.util.List;

public interface ItemService {
    public List<Item> findAll();
    public Item findById(Long id, Integer quantity);
}
