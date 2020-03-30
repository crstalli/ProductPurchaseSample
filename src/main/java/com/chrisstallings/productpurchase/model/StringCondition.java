package com.chrisstallings.productpurchase.model;

import com.chrisstallings.productpurchase.catalog.model.Product;

public class StringCondition extends SimpleCondition<String> {

    public StringCondition(String pv, ConditionOperator co, String val) {
        super(pv, co, val);
    }

    protected String getProductPropertyValue(Product p) {
        return p.getStringAttributes().get(this.propertyName);
    }

}

