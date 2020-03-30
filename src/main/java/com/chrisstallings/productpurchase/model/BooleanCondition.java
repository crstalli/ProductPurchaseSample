package com.chrisstallings.productpurchase.model;

import com.chrisstallings.productpurchase.catalog.model.Product;

public class BooleanCondition extends SimpleCondition<Boolean> {

    public BooleanCondition(String pv, ConditionOperator co, Boolean val){
        super(pv, co, val);
    }

    protected Boolean getProductPropertyValue(Product p){
        return p.getBooleanAttributes().get(this.propertyName);
    }
}

