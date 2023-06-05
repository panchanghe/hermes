package top.javap.hermes.util;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
public final class DigestUtil {

    public static String getServiceId(String name, String group, String version) {
        return name + group + version;
    }
}