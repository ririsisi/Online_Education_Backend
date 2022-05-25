package com.ririsisi.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author ririsisi
 * @Version 1.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.ririsisi"})
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
