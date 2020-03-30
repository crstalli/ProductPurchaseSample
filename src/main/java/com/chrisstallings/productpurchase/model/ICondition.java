package com.chrisstallings.productpurchase.model;

import com.chrisstallings.productpurchase.catalog.model.Product;

public interface ICondition {

    public boolean evaluate(Product p);
}
