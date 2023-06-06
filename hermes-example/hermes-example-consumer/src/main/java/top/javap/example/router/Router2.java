package top.javap.example.router;

import top.javap.hermes.cluster.Router;
import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.remoting.message.Invocation;

import java.util.List;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/6
 **/
//@Component
public class Router2 implements Router {

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public List<Invoker> route(List<Invoker> invokers, Invocation invocation) {
        return invokers;
    }
}