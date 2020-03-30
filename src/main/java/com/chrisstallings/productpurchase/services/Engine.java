package com.chrisstallings.productpurchase.services;

import com.chrisstallings.productpurchase.catalog.model.Product;
import com.chrisstallings.productpurchase.catalog.services.CatalogEvaluator;
import com.chrisstallings.productpurchase.model.Rule;
import com.chrisstallings.productpurchase.model.exception.RuleParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.security.util.Password;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class Engine {

    private final InputParser inputParser;

    private final CatalogEvaluator catalogEvaluator;

    private Map<Product, Double> productScores;

    private Double maxScore = 0.0;

    private Double rulesApplied = 0.0;
    private final Double THRESHOLD_PERCENT = 0.5;

    @Autowired
    public Engine(InputParser ip, CatalogEvaluator ce) {
        inputParser = ip;
        catalogEvaluator = ce;
    }

    public void run() {
        //Populate Score Map
        productScores = catalogEvaluator.getAllProduct().stream().collect(
                Collectors.toMap(Function.identity(), c -> new Double(0)));


        System.out.println("Please type in a rule in the following format (conditon (?(and|or condition)) ... , integer score (ex: 100)) or type exit ");
        try (Scanner sc = new Scanner(System.in)) {
            do {
                String input = sc.nextLine().toLowerCase();
                if (!input.toLowerCase().equals("exit")) {
                    try {
                        applyRule(input);
                        printCurrentResults();
                    } catch (RuleParseException e) {
                        System.out.println("Error, please try again: " + e.getMessage());
                    }
                } else {
                    break;
                }
            } while (sc.hasNextLine());
        }
    }

    private void applyRule(String s) throws RuleParseException {
        Rule rule = inputParser.parseRule(s);
        Set<Product> matching = catalogEvaluator.evaluateRule(rule.rootCondition);
        rulesApplied++;
        maxScore += rule.score;
        for (Product p : productScores.keySet()) {
            Double oldScore = productScores.getOrDefault(p, 0.0);
            double score = matching.contains(p) ? rule.score : 0;
            oldScore += score;
            productScores.put(p, oldScore);
        }
    }

    private void printCurrentResults() {
        Double totalPrice = 0.0;
        int productsMatching = 0;
        double cutoff = maxScore == 0.0 ? 0.0 : (maxScore * THRESHOLD_PERCENT);
        for (Product p : productScores.keySet()) {
            Double d = productScores.getOrDefault(p, 0.0);
            if (d > cutoff) {
                productsMatching++;
                totalPrice += p.getPrice();
            }
        }
        Double averagePrice = productsMatching <= 0 ? 0.0 : totalPrice / productsMatching;
        System.out.println("Total Price: " + String.valueOf(totalPrice));
        System.out.println("Average Price: " + String.valueOf(averagePrice));
    }


}
