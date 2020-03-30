package com.chrisstallings.productpurchase.model;

import com.chrisstallings.productpurchase.catalog.model.Product;

public abstract class SimpleCondition<T extends Comparable> implements ICondition {

    protected String propertyName;

    private ConditionOperator operator;

    private T comparisionValue;

    protected SimpleCondition(String pn, ConditionOperator op, T cv) {
        this.propertyName = pn;
        this.operator = op;
        this.comparisionValue = cv;
    }

    protected abstract T getProductPropertyValue(Product p);

    public boolean evaluate(Product p) {
        T productPropertyValue = getProductPropertyValue(p);
        if (productPropertyValue != null) {
            int compare = productPropertyValue.compareTo(comparisionValue);
            switch (compare) {
                case 0:
                    return operator == ConditionOperator.EQUALS || operator == ConditionOperator.GREATER_THAN_EQUALS || operator == ConditionOperator.LESS_THAN_EQUALS;
                case 1:
                    return operator == ConditionOperator.GREATER_THAN_EQUALS || operator == ConditionOperator.GREATER_THAN;
                case -1:
                    return operator == ConditionOperator.LESS_THAN_EQUALS || operator == ConditionOperator.LESS_THAN_EQUALS;
            }
        }
        return false;
    }

}
