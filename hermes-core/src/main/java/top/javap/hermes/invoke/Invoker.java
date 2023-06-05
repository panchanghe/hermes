package top.javap.hermes.invoke;

import top.javap.hermes.remoting.message.Invocation;

/**
 * @Author: pch
 * @Date: 2023/5/18 12:33
 * @Description:
 */
public interface Invoker {
    Result invoke(Invocation invocation);
}