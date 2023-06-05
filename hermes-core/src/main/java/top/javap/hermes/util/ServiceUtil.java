package top.javap.hermes.util;

import top.javap.hermes.config.ServiceConfig;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
public class ServiceUtil {

    public static String fullName(ServiceConfig config) {
        return config.getName() + ":" + config.getGroup() + ":" + config.getVersion();
    }
}