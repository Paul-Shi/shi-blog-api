package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DemoApplication
 */
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        /**
         * ElasticSearch 所需的临时设置，待解决
         */
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(DemoApplication.class, args);
    }
}
