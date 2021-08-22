package com.reesecake.simple_hsk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class SimpleHskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleHskApplication.class, args);
    }

}
