package top.javap.hermes.invoke.local;

import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.invoke.Result;
import top.javap.hermes.invoke.SimpleResult;
import top.javap.hermes.remoting.message.Invocation;

import java.lang.reflect.Method;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/23
 **/
public class ReflectionInvoker implements Invoker {

    private final Object service;
    private final Method method;

    public ReflectionInvoker(Object service, Method method) {
        this.service = service;
        this.method = method;
    }

    @Override
    public Result invoke(Invocation invocation) {
        try {
            Object result = method.invoke(service, invocation.arguments());
            return SimpleResult.success(result);
        } catch (Exception e) {
            return SimpleResult.error(e);
        }
    }
}