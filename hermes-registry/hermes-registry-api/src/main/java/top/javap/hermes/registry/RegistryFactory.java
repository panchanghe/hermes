package top.javap.hermes.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
public final class RegistryFactory {
    private static final Logger log = LoggerFactory.getLogger(RegistryFactory.class);
    private static final Map<String, String> STRATEGY = new HashMap<>();
    private static final ConcurrentMap<RegistryConfig, Registry> CACHE = new ConcurrentHashMap<>();

    static {
        STRATEGY.put("nacos", "top.javap.hermes.registry.NacosRegistry");
    }

    public static Registry getRegistry(RegistryConfig config) {
        Registry registry = CACHE.get(config);
        if (Objects.isNull(registry)) {
            synchronized (RegistryFactory.class) {
                if (Objects.isNull(registry)) {
                    CACHE.put(config, registry = createRegistry(config));
                }
            }
        }
        return registry;
    }

    private static Registry createRegistry(RegistryConfig config) {
        try {
            Constructor<Registry> constructor = (Constructor<Registry>) Class.forName(STRATEGY.get(config.getProtocol())).getConstructor(RegistryConfig.class);
            return constructor.newInstance(config);
        } catch (Exception e) {
            log.error("registry instantiation error", e);
            throw new RuntimeException(e);
        }
    }
}