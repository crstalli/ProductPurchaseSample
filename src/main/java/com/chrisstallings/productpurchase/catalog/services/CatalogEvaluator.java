package com.chrisstallings.productpurchase.catalog.services;

import com.chrisstallings.productpurchase.catalog.model.Product;
import com.chrisstallings.productpurchase.model.ICondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CatalogEvaluator {

    private final Catalog catalog;


    @Autowired
    CatalogEvaluator(Catalog c) {
        catalog = c;
    }

    public Set<Product> getAllProduct() {
        return catalog.getProducts();
    }

    public Set<Product> evaluateRule(ICondition condition) {
        Set<Product> products = new HashSet<>();
        for (Product p : catalog.getProducts()) {
            if (condition.evaluate(p)) {
                products.add(p);
            }
        }
        return products;
    }


}
