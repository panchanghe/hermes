package top.javap.hermes.cluster.support;

import top.javap.hermes.cluster.LoadBalance;
import top.javap.hermes.cluster.ServiceList;
import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.invoke.Result;
import top.javap.hermes.remoting.message.Invocation;
import top.javap.hermes.util.Assert;

import java.util.List;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/27
 **/
public class AbstractClusterInvoker implements ClusterInvoker {

    private final ServiceList serviceList;
    private final LoadBalance loadBalance;

    public AbstractClusterInvoker(ServiceList serviceList, LoadBalance loadBalance) {
        this.serviceList = serviceList;
        this.loadBalance = loadBalance;
    }

    @Override
    public ServiceList getServiceList() {
        return serviceList;
    }

    @Override
    public Result invoke(Invocation invocation) {
        List<Invoker> invokers = serviceList.list(invocation);
        Assert.notEmpty(invokers, "not found available provider");
        final Invoker invoker = loadBalance.select(invokers, invocation);
        Result result = invoker.invoke(invocation);
        return result;
    }
}