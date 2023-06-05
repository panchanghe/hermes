package top.javap.hermes.cluster;

import top.javap.hermes.invoke.Invoker;

/**
 * @Author: pch
 * @Date: 2023/5/28 15:30
 * @Description:
 */
public interface Cluster {
    Invoker aggregation(ServiceList serviceList, LoadBalance loadBalance);
}