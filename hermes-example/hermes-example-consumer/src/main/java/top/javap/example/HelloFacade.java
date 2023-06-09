package top.javap.example;

import org.springframework.stereotype.Component;
import top.javap.example.service.UserService;
import top.javap.hermes.spring.annotation.HermesReference;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/2
 **/
@Component
public class HelloFacade {

    @HermesReference(application = "app-1", group = "g1", version = "v1")
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }
}