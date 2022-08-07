package com.example.instagramjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InstagramJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstagramJpaApplication.class, args);
        long heapSize = Runtime.getRuntime().totalMemory();
        System.out.println("HEAP Size(M) : "+ heapSize / (1024*1024) + " MB");
    }

}
