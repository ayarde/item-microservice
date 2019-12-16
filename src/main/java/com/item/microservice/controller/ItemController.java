package com.item.microservice.controller;

import com.item.microservice.model.Item;
import com.item.microservice.model.Product;
import com.item.microservice.model.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RefreshScope
@RestController
public class ItemController {

    private static Logger log = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    private Environment environment;

    @Autowired
    //@Qualifier("serviceRestTemplate")
    @Qualifier("serviceFeign")
    private ItemService itemService;

    @Value("${configuration.text}")
    private String text;

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

    @GetMapping("/get-config")
    public ResponseEntity<?> getConfiguration(@Value("${server.port}") String port) {
        log.info(text);

        Map<String, String> json = new HashMap<>();
        json.put("text", text);
        json.put("port", port);
        if(environment.getActiveProfiles().length > 0 && environment.getActiveProfiles()[0].equals("dev")){
            json.put("author.name", environment.getProperty("configuration.author.name"));
            json.put("author.email", environment.getProperty("configuration.author.email"));
        }
        return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
    }

}
