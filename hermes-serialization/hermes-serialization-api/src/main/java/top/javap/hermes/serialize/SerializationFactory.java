package top.javap.hermes.serialize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/28
 **/
public class SerializationFactory {
    private static final Logger log = LoggerFactory.getLogger(SerializationFactory.class);
    private static final ConcurrentMap<String, Serialization> CACHE = new ConcurrentHashMap<>();

    public static Serialization getSerialization(String clazz) {
        Serialization serialization = CACHE.get(clazz);
        if (Objects.isNull(serialization)) {
            synchronized (SerializationFactory.class) {
                serialization = CACHE.get(clazz);
                if (Objects.isNull(serialization)) {
                    CACHE.put(clazz, serialization = createSerialization(clazz));
                }
            }
        }
        return serialization;
    }

    private static Serialization createSerialization(String clazz) {
        try {
            Class<Serialization> serializationClass = (Class<Serialization>) Class.forName(clazz);
            return serializationClass.newInstance();
        } catch (Exception e) {
            log.error("not found serializer:" + clazz, e);
            throw new RuntimeException(e);
        }
    }
}