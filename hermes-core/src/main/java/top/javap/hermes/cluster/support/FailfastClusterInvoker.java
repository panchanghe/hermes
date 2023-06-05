package top.javap.hermes.cluster.support;

import top.javap.hermes.cluster.LoadBalance;
import top.javap.hermes.cluster.ServiceList;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/27
 **/
public class FailfastClusterInvoker extends AbstractClusterInvoker {

    public FailfastClusterInvoker(ServiceList serviceList, LoadBalance loadBalance) {
        super(serviceList, loadBalance);
    }
}