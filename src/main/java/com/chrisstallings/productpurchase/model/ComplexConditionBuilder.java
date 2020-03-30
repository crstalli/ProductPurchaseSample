package com.chrisstallings.productpurchase.model;

public class ComplexConditionBuilder {
    private ICondition leftHandCondition;
    private ComplexConditionOperator operator;
    private ICondition rightHandCondition;

    public ComplexConditionBuilder setLeftHandCondition(ICondition leftHandCondition) {
        this.leftHandCondition = leftHandCondition;
        return this;
    }

    public ComplexConditionBuilder setOperator(ComplexConditionOperator operator) {
        this.operator = operator;
        return this;
    }

    public ComplexConditionBuilder setRightHandCondition(ICondition rightHandCondition) {
        this.rightHandCondition = rightHandCondition;
        return this;
    }

    public ComplexCondition createComplexCondition() {
        return new ComplexCondition(leftHandCondition, operator, rightHandCondition);
    }
}
