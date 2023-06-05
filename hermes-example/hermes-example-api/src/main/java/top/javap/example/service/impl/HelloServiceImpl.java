package top.javap.example.service.impl;

import top.javap.example.domain.User;
import top.javap.example.service.HelloService;
import top.javap.hermes.spring.annotation.HermesService;

import java.util.concurrent.CompletableFuture;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
@HermesService(group = "g1", version = "v1")
public class HelloServiceImpl implements HelloService {

    public String say(String name) {
//        System.err.println(RpcContext.getAttachment("traceId"));
        System.err.println("server hi " + name);
//        int i = 1 / 0;
        return "Hi " + name;
    }

    @Override
    public CompletableFuture<String> asyncSay(String name) {
        System.err.println("server async hi " + name);
        return CompletableFuture.supplyAsync(() -> {
//            int i = 1 / 0;
            return "Hi async " + name;
        });
    }

    @Override
    public void oneway(String name) {
        System.err.println("oneway Hi " + name);
    }

    @Override
    public User exchange(User user) {
        User newUser = new User();
        newUser.setName("new " + user.getName());
        newUser.setAge(user.getAge() + 1);
        return newUser;
    }
}