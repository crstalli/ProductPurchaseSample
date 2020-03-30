package com.chrisstallings.productpurchase.model;

import com.chrisstallings.productpurchase.catalog.model.Product;

public class ComplexCondition implements ICondition {
    ICondition left;
    ComplexConditionOperator operator;
    ICondition right;

    public ComplexCondition(ICondition leftHandCondition, ComplexConditionOperator operator, ICondition rightHandCondition) {
        this.left = leftHandCondition;
        this.operator = operator;
        this.right = rightHandCondition;
    }

    @Override
    public boolean evaluate(Product p) {
        if (right == null) {
            return left.evaluate(p);
        } else {
            if (operator == operator.AND) {
                return left.evaluate(p) && right.evaluate(p);
            } else if (operator == operator.OR) {
                return left.evaluate(p) || right.evaluate(p);
            } else {
                System.out.println("Something went wrong in evaluation");
                return false;
            }
        }
    }
}
