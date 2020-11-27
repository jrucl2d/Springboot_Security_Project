package com.yu.security1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Security1Application {
    public static final String APPLICATION_LOCATIONS = "spring.config.location=" + "classpath:application.yml,"
            + "C:\\Users\\ysk78\\src\\java\\spring\\security\\security1\\src\\main\\resources\\config.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(Security1Application.class).properties(APPLICATION_LOCATIONS).run(args);
    }
}
