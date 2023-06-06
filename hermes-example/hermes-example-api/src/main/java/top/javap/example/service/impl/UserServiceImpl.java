package top.javap.example.service.impl;

import top.javap.example.domain.User;
import top.javap.example.service.UserService;
import top.javap.hermes.spring.annotation.HermesService;

import java.util.concurrent.CompletableFuture;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
@HermesService(group = "g1", version = "v1")
public class UserServiceImpl implements UserService {

    @Override
    public User get(String name) {
        User user = new User();
        user.setName(name);
        user.setAge(18);
        return user;
    }

    @Override
    public CompletableFuture<User> getByAsync(String name) {
        return CompletableFuture.supplyAsync(() -> {
            User user = new User();
            user.setName(name);
            user.setAge(18);
            return user;
        });
    }

    @Override
    public void oneway(User user) {
        System.err.println("oneway receive:" + user);
    }
}