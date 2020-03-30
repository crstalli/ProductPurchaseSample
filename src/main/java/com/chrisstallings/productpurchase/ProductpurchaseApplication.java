package com.chrisstallings.productpurchase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductpurchaseApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ProductpurchaseApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
//		SpringApplication.run(ProductpurchaseApplication.class, args);
    }

}
