package top.javap.hermes.registry;

import top.javap.hermes.common.Properties;

/**
 * @Author: pch
 * @Date: 2023/5/17 20:41
 * @Description:
 */
public interface Registry {

    void register(Properties properties);

    void unregister();

    void subscribe(String serviceName, NotifyListener listener);
}