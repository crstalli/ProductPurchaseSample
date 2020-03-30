package com.chrisstallings.productpurchase.model.exception;

public class RuleParseException extends Exception {

    public RuleParseException(int reasonCode){
        super(String.format("The Rule could not be parsed: reason code: %d , please make sure it is in the correct format", reasonCode) );
    }

    public RuleParseException(int reasonCode, Exception e){
        super(String.format("The rule could not be parsed: reason code: %d , please make sure it is in the correct format", reasonCode), e);
    }
}
