package top.javap.example.service;

import top.javap.example.domain.User;
import top.javap.hermes.annotation.RpcMethod;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: pch
 * @Date: 2023/5/18 14:23
 * @Description:
 */
public interface UserService {

    User get(String name);

    CompletableFuture<User> getByAsync(String name);

    @RpcMethod(oneway = true)
    void oneway(User user);
}