package top.javap.hermes.cluster.support;

import top.javap.hermes.cluster.ServiceList;
import top.javap.hermes.common.Properties;
import top.javap.hermes.constant.DictConstant;
import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.protocol.Protocol;
import top.javap.hermes.protocol.ProtocolFactory;
import top.javap.hermes.registry.NotifyListener;
import top.javap.hermes.registry.Registry;
import top.javap.hermes.remoting.message.Invocation;
import top.javap.hermes.remoting.transport.Transporter;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 基于注册中心的服务列表
 *
 * @author: pch
 * @description:
 * @date: 2023/5/28
 **/
public class RegistryServiceList implements ServiceList {

    private final Registry registry;
    private final String subscribeService;
    private final Transporter transporter;
    private List<Invoker> invokers;

    public RegistryServiceList(Registry registry, String subscribeService, Transporter transporter) {
        this.registry = registry;
        this.subscribeService = subscribeService;
        this.transporter = transporter;
        subscribeService();
    }

    @Override
    public List<Invoker> list(Invocation invocation) {
        // todo route
        return invokers;
    }

    private void subscribeService() {
        try {
            CountDownLatch cdl = new CountDownLatch(1);
            registry.subscribe(subscribeService, new NotifyListener() {
                @Override
                public void notify(List<Properties> list) {
                    List<Invoker> newList = list.stream().map(p -> {
                        Protocol protocol = ProtocolFactory.getProtocol(p.get(DictConstant.PROTOCOL));
                        p.put(DictConstant.TRANSPORT, transporter);
                        return (Invoker) protocol.refer(p);
                    }).collect(Collectors.toList());
                    invokers = newList;
                    cdl.countDown();
                }
            });
            cdl.await(10L, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}