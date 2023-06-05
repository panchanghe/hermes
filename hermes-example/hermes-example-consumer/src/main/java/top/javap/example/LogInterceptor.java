package top.javap.example;

import top.javap.hermes.interceptor.Interceptor;
import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.invoke.Result;
import top.javap.hermes.remoting.message.Invocation;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/2
 **/
//@Component
//@Intercept(applyScope = Scope.CONSUMER)
public class LogInterceptor implements Interceptor {

    @Override
    public Result intercept(Invoker invoker, Invocation invocation) {
        System.err.println("before....");
        Result result = invoker.invoke(invocation);
        System.err.println("after....");
        return result;
    }
}