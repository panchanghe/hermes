package top.javap.hermes.interceptor;

import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.invoke.Result;
import top.javap.hermes.remoting.message.Invocation;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/1
 **/
public class InterceptorInvoker implements Invoker {

    private final Invoker invoker;
    private final Interceptor interceptor;

    public InterceptorInvoker(Invoker invoker, Interceptor interceptor) {
        this.invoker = invoker;
        this.interceptor = interceptor;
    }

    @Override
    public Result invoke(Invocation invocation) {
        return interceptor.intercept(invoker, invocation);
    }
}