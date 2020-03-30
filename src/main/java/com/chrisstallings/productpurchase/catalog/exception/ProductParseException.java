package com.chrisstallings.productpurchase.catalog.exception;

public class ProductParseException extends Exception {

    public ProductParseException(int reasonCode) {
        super(String.format("The product catalog could not be parsed: reason code: %d , please make sure it is in the correct format", reasonCode));
    }

    public ProductParseException(int reasonCode, Exception e) {
        super(String.format("The product catalog could not be parsed: reason code: %d , please make sure it is in the correct format", reasonCode), e);
    }
}
