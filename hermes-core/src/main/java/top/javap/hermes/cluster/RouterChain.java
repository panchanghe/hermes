package top.javap.hermes.cluster;

import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.remoting.message.Invocation;
import top.javap.hermes.util.CollectionUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/6
 **/
public class RouterChain implements Router {

    private List<Router> routers = new LinkedList<>();

    @Override
    public List<Invoker> route(List<Invoker> invokers, Invocation invocation) {
        if (CollectionUtil.isNotEmpty(routers)) {
            for (Router router : routers) {
                invokers = router.route(invokers, invocation);
            }
        }
        return invokers;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public void addRouter(Router router) {
        routers.add(router);
    }

    public void addRouter(List<Router> routers) {
        this.routers.addAll(routers);
    }
}