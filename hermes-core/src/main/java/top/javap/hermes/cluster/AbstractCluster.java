package top.javap.hermes.cluster;

import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.util.Assert;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/28
 **/
public abstract class AbstractCluster implements Cluster {

    @Override
    public final Invoker aggregation(ServiceList serviceList, LoadBalance loadBalance) {
        Assert.notNull(serviceList, "ServiceList can not be null");
        Assert.notNull(loadBalance, "LoadBalance can not be null");
        return createClusterInvoker(serviceList, loadBalance);
    }

    protected abstract Invoker createClusterInvoker(ServiceList serviceList, LoadBalance loadBalance);
}