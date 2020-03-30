package com.chrisstallings.productpurchase.model;

import java.util.HashMap;
import java.util.Map;

public enum ConditionOperator {

    GREATER_THAN_EQUALS(">="),
    GREATER_THAN(">"),
    EQUALS("=="),
    LESS_THAN_EQUALS("<="),
    LESS_THAN("<");

    private String operatorValue;

    ConditionOperator(String s) {
        operatorValue = s;
    }

    public String value() {
        return operatorValue;
    }

    private static Map<String, ConditionOperator> valueMap = new HashMap<>();

    static {
        for (ConditionOperator d : ConditionOperator.values()) {
            valueMap.put(d.value(), d);
        }
    }

    //Enum from String
    public static ConditionOperator get(String operator) {
        return valueMap.get(operator);
    }


}
