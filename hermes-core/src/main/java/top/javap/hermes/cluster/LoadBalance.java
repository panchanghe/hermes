package top.javap.hermes.cluster;

import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.remoting.message.Invocation;

import java.util.List;

/**
 * @Author: pch
 * @Date: 2023/5/28 17:37
 * @Description:
 */
public interface LoadBalance {

    Invoker select(List<Invoker> invokers, Invocation invocation);

}