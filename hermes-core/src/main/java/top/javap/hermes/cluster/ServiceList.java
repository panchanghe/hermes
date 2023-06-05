package top.javap.hermes.cluster;

import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.remoting.message.Invocation;

import java.util.List;

/**
 * 服务列表
 *
 * @Author: pch
 * @Date: 2023/5/28 16:33
 * @Description:
 */
public interface ServiceList {

    List<Invoker> list(Invocation invocation);

}