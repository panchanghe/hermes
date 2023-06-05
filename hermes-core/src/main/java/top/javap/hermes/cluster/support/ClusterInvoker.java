package top.javap.hermes.cluster.support;

import top.javap.hermes.cluster.ServiceList;
import top.javap.hermes.invoke.Invoker;

/**
 * @Author: pch
 * @Date: 2023/5/27 14:45
 * @Description:
 */
public interface ClusterInvoker extends Invoker {

    ServiceList getServiceList();
}