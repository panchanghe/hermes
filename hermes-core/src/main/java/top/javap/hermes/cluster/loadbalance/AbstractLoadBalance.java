package top.javap.hermes.cluster.loadbalance;

import top.javap.hermes.cluster.LoadBalance;
import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.remoting.message.Invocation;

import java.util.List;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/28
 **/
public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public Invoker select(List<Invoker> invokers, Invocation invocation) {
        if (invokers.size() == 1) {
            return invokers.get(0);
        }
        return doSelect(invokers, invocation);
    }

    protected abstract Invoker doSelect(List<Invoker> invokers, Invocation invocation);
}