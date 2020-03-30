package com.chrisstallings.productpurchase.model;

import com.chrisstallings.productpurchase.catalog.model.Product;

public class DoubleCondition extends SimpleCondition<Double> {

    public DoubleCondition(String pv, ConditionOperator co, Double val) {
        super(pv, co, val);
    }

    protected Double getProductPropertyValue(Product p) {
        if (this.propertyName.equals("price")) {
            return p.getPrice();
        } else if (this.propertyName.equals("id")) {
            return p.getId();
        } else {
            return p.getNumberAttributes().get(this.propertyName);
        }
    }
}


