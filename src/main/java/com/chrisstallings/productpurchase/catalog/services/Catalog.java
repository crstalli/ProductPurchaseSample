package com.chrisstallings.productpurchase.catalog.services;

import com.chrisstallings.productpurchase.catalog.ProductBuilder;
import com.chrisstallings.productpurchase.catalog.exception.ProductParseException;
import com.chrisstallings.productpurchase.catalog.model.Product;
import com.chrisstallings.productpurchase.utils.StringUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class Catalog {

    private final String HEADER = "id|name|price|attributes";

    private final String FILE_LOCATION;

    @Getter
    private Set<Product> products;

    @Autowired
    Catalog(@Value("${app.catalogFile}") String catalogFile) {
        FILE_LOCATION = catalogFile;
        products = new HashSet<>();
    }

    @PostConstruct
    public void parseProducts() {
        try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource(FILE_LOCATION).toURI()))) {
            stream.forEach(s -> {
                try {
                    addProduct(s.toLowerCase());
                } catch (ProductParseException e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (IOException | URISyntaxException e) {
            System.out.println("Sorry, Could not find catalog!");
        }

    }

    private void addProduct(String s) throws ProductParseException {
        if (!s.equals(HEADER)) {
            String[] values = s.split("\\|");
            if (values.length != 4) {
                throw new ProductParseException(1);
            }
            products.add(createProduct(values));
        }
    }

    private Product createProduct(String[] values) throws ProductParseException {
        ProductBuilder pb = new ProductBuilder();
        try {
            pb = pb.setId(Double.valueOf(values[0]))
                    .setName(values[1])
                    .setPrice(Double.valueOf(values[2]));
            pb = populateAttributes(values[3], pb);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new ProductParseException(2, e);
        }
        return pb.createProduct();
    }

    private ProductBuilder populateAttributes(String s, ProductBuilder pb) throws ProductParseException {
        Map<String, String> sa = new HashMap<>();
        Map<String, Double> na = new HashMap<>();
        Map<String, Boolean> ba = new HashMap<>();
        String[] attribs = s.split(",");
        try {
            for (String avPair : attribs) {
                String[] pair = avPair.split("=");
                if (pair[0].trim().equals(pair[0].trim().replaceAll(".*\\\\W+.*", ""))) {
                    if (StringUtils.isDouble(pair[1].trim())) {
                        if (pair[0].trim().equals("price") || pair[0].trim().equals("id")) {
                            throw new ProductParseException(5);
                        }
                        na.put(pair[0].trim(), Double.parseDouble(pair[1].trim()));
                    } else if (StringUtils.isBoolean(pair[1].trim())) {
                        ba.put(pair[0].trim(), Boolean.parseBoolean(pair[1].trim()));
                    } else {
                        if (pair[0].equals("name")) {
                            throw new ProductParseException(5);
                        }
                        sa.put(pair[0].trim(), pair[1]);
                    }
                } else {
                    throw new ProductParseException(3);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ProductParseException(4);
        }
        return pb.setNumberAttributes(na).setBooleanAttributes(ba).setStringAttributes(sa);
    }


}
