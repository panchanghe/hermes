package top.javap.hermes.spring.core;

import top.javap.hermes.config.ReferenceConfig;
import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.invoke.InvokerDelegate;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/2
 **/
public class InvokerDelegateManager {

    private static final ConcurrentMap<ReferenceConfig, InvokerDelegate> CACHE = new ConcurrentHashMap<>();

    public static void put(ReferenceConfig referenceConfig, InvokerDelegate invokerDelegate) {
        CACHE.put(referenceConfig, invokerDelegate);
    }

    public static void setInvoker(ReferenceConfig referenceConfig, Invoker invoker) {
        InvokerDelegate invokerDelegate = CACHE.get(referenceConfig);
        if (Objects.nonNull(invokerDelegate)) {
            invokerDelegate.setDelegate(invoker);
        }
    }

    public static Set<ReferenceConfig> referenceConfigSet() {
        return CACHE.keySet();
    }
}