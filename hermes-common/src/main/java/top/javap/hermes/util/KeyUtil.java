package top.javap.hermes.util;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.zip.CRC32;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
public final class KeyUtil {

    private static final ConcurrentMap<String, Integer> KEY_CACHE = new ConcurrentHashMap<>();

    public static int methodKey(String service, String group, String version, String methodDesc) {
        String s = service + ":" + group + ":" + version + ":" + methodDesc;
        Integer key = KEY_CACHE.get(s);
        if (Objects.isNull(key)) {
            CRC32 crc32 = new CRC32();
            crc32.update(s.getBytes(StandardCharsets.UTF_8));
            key = (int) crc32.getValue();
            KEY_CACHE.put(s, key);
        }
        return key;
    }

    public static int methodKey(String service, String group, String version, Method method) {
        return methodKey(service, group, version, MethodUtil.getMethodDescription(method));
    }
}