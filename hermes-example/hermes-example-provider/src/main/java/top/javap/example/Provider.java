package top.javap.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/2
 **/
@SpringBootApplication(scanBasePackages = "top.javap.example")
//@EnableHermes(scanPackages = "top.javap.example.service.impl")
public class Provider {
    public static void main(String[] args) {
        SpringApplication.run(Provider.class, args);
    }
}