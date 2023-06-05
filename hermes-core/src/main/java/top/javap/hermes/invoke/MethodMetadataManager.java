package top.javap.hermes.invoke;

import top.javap.hermes.config.ServiceConfig;
import top.javap.hermes.metadata.MethodMetadata;
import top.javap.hermes.metadata.MethodMetadataParser;
import top.javap.hermes.metadata.ReflectMethodMetadataParser;
import top.javap.hermes.util.KeyUtil;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
public final class MethodMetadataManager {

    private static final MethodMetadataParser parser = new ReflectMethodMetadataParser();
    private static final ConcurrentMap<Method, MethodMetadata> CACHE = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Integer, MethodMetadata> KEY_CACHE = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Integer, ServiceConfig> KEY_SERVICE_CACHE = new ConcurrentHashMap<>();

    public static MethodMetadata register(ServiceConfig serviceConfig, Method method) {
        MethodMetadata metadata = parser.parse(method);
        final int key = KeyUtil.methodKey(serviceConfig.getName(), serviceConfig.getGroup(), serviceConfig.getVersion(), method);
        metadata.setKey(key);
        KEY_CACHE.put(key, metadata);
        KEY_SERVICE_CACHE.put(key, serviceConfig);
        return metadata;
    }

    public static void register(Method method) {
        CACHE.put(method, parser.parse(method));
    }

    public static MethodMetadata getMetadata(Method method) {
        MethodMetadata metadata = CACHE.get(method);
        if (Objects.isNull(metadata)) {
            synchronized (MethodMetadataManager.class) {
                metadata = CACHE.get(method);
                if (Objects.isNull(metadata)) {
                    register(method);
                    metadata = CACHE.get(method);
                }
            }
        }
        return metadata;
    }

    public static MethodMetadata getMethodMetadata(int key) {
        return KEY_CACHE.get(key);
    }

    public static ServiceConfig getServiceConfig(int key) {
        return KEY_SERVICE_CACHE.get(key);
    }
}