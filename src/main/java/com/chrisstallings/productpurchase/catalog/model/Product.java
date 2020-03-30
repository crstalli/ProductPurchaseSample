package com.chrisstallings.productpurchase.catalog.model;


import lombok.Getter;

import java.util.Map;


public class Product {
    public Product() {
    }

    public Product(Double id, String name, Double price, Map<String, String> sa, Map<String, Double> na, Map<String, Boolean> ba) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stringAttributes = sa;
        this.booleanAttributes = ba;
        this.numberAttributes = na;
    }

    @Getter
    Double id;
    @Getter
    String name;
    @Getter
    Double price;

    @Getter
    Map<String, String> stringAttributes;
    @Getter
    Map<String, Double> numberAttributes;
    @Getter
    Map<String, Boolean> booleanAttributes;


}
