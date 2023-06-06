package top.javap.hermes.cluster;

import top.javap.hermes.common.Ordered;
import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.remoting.message.Invocation;

import java.util.List;

/**
 * 服务路由
 *
 * @Author: pch
 * @Date: 2023/6/6 16:20
 * @Description:
 */
public interface Router extends Ordered {

    List<Invoker> route(List<Invoker> invokers, Invocation invocation);

}