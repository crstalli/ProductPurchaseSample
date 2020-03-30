package com.chrisstallings.productpurchase.catalog;

import com.chrisstallings.productpurchase.catalog.model.Product;

import java.util.Map;

public class ProductBuilder {
    private Double id;
    private String name;
    private Double price;
    private Map<String, String> sa;
    private Map<String, Double> na;
    private Map<String, Boolean> ba;

    public ProductBuilder setId(Double id) {
        this.id = id;
        return this;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setPrice(Double price) {
        this.price = price;
        return this;
    }

    public ProductBuilder setStringAttributes(Map<String, String> sa) {
        this.sa = sa;
        return this;
    }

    public ProductBuilder setNumberAttributes(Map<String, Double> na) {
        this.na = na;
        return this;
    }

    public ProductBuilder setBooleanAttributes(Map<String, Boolean> ba) {
        this.ba = ba;
        return this;
    }

    public Product createProduct() {
        return new Product(id, name, price, sa, na, ba);
    }
}
