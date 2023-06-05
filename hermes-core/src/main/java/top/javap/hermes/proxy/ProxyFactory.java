package top.javap.hermes.proxy;

import top.javap.hermes.config.ReferenceConfig;
import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.reflect.RpcInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/27
 **/
public class ProxyFactory {

    public static <T> T getProxy(Invoker invoker, ReferenceConfig<T> referenceConfig) {
        return (T) Proxy.newProxyInstance(Invoker.class.getClassLoader(),
                new Class[]{referenceConfig.getInterfaceClass()},
                new RpcInvocationHandler(referenceConfig, invoker));
    }
}