package top.javap.hermes.cluster;

import top.javap.hermes.cluster.support.FailfastClusterInvoker;
import top.javap.hermes.invoke.Invoker;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/28
 **/
public class FailfastCluster extends AbstractCluster {

    public static final String NAME = "failfast";

    @Override
    protected Invoker createClusterInvoker(ServiceList serviceList, LoadBalance loadBalance) {
        return new FailfastClusterInvoker(serviceList, loadBalance);
    }
}