package com.chrisstallings.productpurchase.services;

import com.chrisstallings.productpurchase.model.BooleanCondition;
import com.chrisstallings.productpurchase.model.ComplexConditionBuilder;
import com.chrisstallings.productpurchase.model.ComplexConditionOperator;
import com.chrisstallings.productpurchase.model.ConditionOperator;
import com.chrisstallings.productpurchase.model.DoubleCondition;
import com.chrisstallings.productpurchase.model.ICondition;
import com.chrisstallings.productpurchase.model.Rule;
import com.chrisstallings.productpurchase.model.StringCondition;
import com.chrisstallings.productpurchase.model.exception.RuleParseException;

import com.chrisstallings.productpurchase.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Condition;

@Component
public class InputParser {


    public Rule parseRule(String rawRuleText) throws RuleParseException {
        String[] ruleAndScore = rawRuleText.split(",");
        if (ruleAndScore.length != 2) {
            throw new RuleParseException(1);
        }
        Integer score = null;
        try {
            score = Integer.parseInt(ruleAndScore[1].trim());
        } catch (NumberFormatException e) {
            new RuleParseException(2, e);
        }
        return new Rule(score, createConditions(ruleAndScore[0].trim()));
    }

    ICondition createConditions(String parseRule) throws RuleParseException {
        parseRule = parseRule.trim();
        int firstAndIndex = parseRule.indexOf("&&");
        int firstOrIndex = parseRule.indexOf("||");

        if (firstAndIndex >= 0 || firstOrIndex >= 0) {
            return parseComplexCondition(parseRule, firstAndIndex, firstOrIndex);
        } else if (parseRule.length() > 0) {
            return parseSimpleCondition(parseRule);
        } else {
            //0 length condition
            throw new RuleParseException(3);
        }
    }

    ICondition parseComplexCondition(String parseRule, int firstAndIndex, int firstOrIndex) throws RuleParseException {
        int indexToUse;
        ComplexConditionBuilder builder = new ComplexConditionBuilder();
        if (firstAndIndex < firstOrIndex || firstOrIndex == -1) {
            indexToUse = firstAndIndex;
            builder.setOperator(ComplexConditionOperator.AND);
        } else {
            indexToUse = firstOrIndex;
            builder.setOperator(ComplexConditionOperator.OR);
        }
        builder.setLeftHandCondition(
                parseSimpleCondition(
                        parseRule.substring(0, indexToUse).trim()
                )
        );
        builder.setRightHandCondition(
                createConditions(
                        parseRule.substring(indexToUse + 2).trim()
                )
        );
        return builder.createComplexCondition();
    }

    ICondition parseSimpleCondition(String conditionStr) throws RuleParseException {
        String[] conditionParts = conditionStr.split("\\s+");
        if (conditionParts.length != 3) {
            throw new RuleParseException(3);
        }
        ConditionOperator operator;
        try {
            operator = ConditionOperator.get(conditionParts[1].trim());

            if (StringUtils.isDouble(conditionParts[2].trim())) {
                return new DoubleCondition(conditionParts[0].trim(), operator, Double.parseDouble(conditionParts[2].trim()));
            } else if (StringUtils.isBoolean(conditionParts[2].trim())) {
                return new BooleanCondition(conditionParts[0].trim(), operator, Boolean.valueOf(conditionParts[2].trim()));
            } else {
                return new StringCondition(conditionParts[0].trim(), operator, conditionParts[2].trim());
            }
        } catch (IllegalArgumentException e) {
            throw new RuleParseException(4, e);
        }
    }

}
