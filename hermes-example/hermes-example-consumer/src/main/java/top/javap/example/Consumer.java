package top.javap.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author: pch
 * @description:
 * @date: 2023/6/2
 **/
@SpringBootApplication(scanBasePackages = {"top.javap.example"})
public class Consumer {
    public static void main(String[] args) {
        SpringApplication.run(Consumer.class, args);
    }
}