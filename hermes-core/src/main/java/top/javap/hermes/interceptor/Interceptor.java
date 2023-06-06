package top.javap.hermes.interceptor;

import top.javap.hermes.common.Ordered;
import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.invoke.Result;
import top.javap.hermes.remoting.message.Invocation;

/**
 * 拦截器
 *
 * @Author: pch
 * @Date: 2023/6/1 14:31
 * @Description:
 */
public interface Interceptor extends Ordered {

    Result intercept(Invoker invoker, Invocation invocation);
}