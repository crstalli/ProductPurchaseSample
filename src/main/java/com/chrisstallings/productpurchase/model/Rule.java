package com.chrisstallings.productpurchase.model;

import lombok.Getter;
import lombok.Setter;

public class Rule {
    @Getter
    @Setter
    public Integer score;

    @Getter
    @Setter
    public ICondition rootCondition;

    public Rule(Integer score, ICondition rootCondition) {
        this.rootCondition = rootCondition;
        this.score = score;
    }

}
