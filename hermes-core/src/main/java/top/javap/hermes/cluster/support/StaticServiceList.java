package top.javap.hermes.cluster.support;

import top.javap.hermes.cluster.ServiceList;
import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.remoting.message.Invocation;

import java.util.List;

/**
 * 静态服务列表
 *
 * @author: pch
 * @description:
 * @date: 2023/5/28
 **/
public class StaticServiceList implements ServiceList {

    private final List<Invoker> invokers;

    public StaticServiceList(List<Invoker> invokers) {
        this.invokers = invokers;
    }

    @Override
    public List<Invoker> list(Invocation invocation) {
        return invokers;
    }
}