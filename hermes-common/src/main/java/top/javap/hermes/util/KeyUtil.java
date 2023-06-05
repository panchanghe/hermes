package top.javap.hermes.util;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
public final class KeyUtil {

    public static int methodKey(String service, String group, String version, String methodDesc) {
        CRC32 crc32 = new CRC32();
        crc32.update((service + ":" + group + ":" + version + ":" + methodDesc).getBytes(StandardCharsets.UTF_8));
        return (int) crc32.getValue();
    }

    public static int methodKey(String service, String group, String version, Method method) {
        return methodKey(service, group, version, MethodUtil.getMethodDescription(method));
    }
}