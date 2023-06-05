package top.javap.hermes.cluster.loadbalance;

import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.remoting.message.Invocation;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/28
 **/
public class RandomLoadBalance extends AbstractLoadBalance {

    public static final String NAME = "random";

    @Override
    protected Invoker doSelect(List<Invoker> invokers, Invocation invocation) {
        int length = invokers.size();
        return invokers.get(ThreadLocalRandom.current().nextInt(length));
    }
}