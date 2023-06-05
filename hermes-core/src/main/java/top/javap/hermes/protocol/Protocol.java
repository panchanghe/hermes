package top.javap.hermes.protocol;

import top.javap.hermes.application.Application;
import top.javap.hermes.common.Properties;
import top.javap.hermes.invoke.Invoker;

/**
 * @Author: pch
 * @Date: 2023/5/17 20:05
 * @Description:
 */
public interface Protocol {
    void export(Application application);

    Invoker refer(Properties properties);
}