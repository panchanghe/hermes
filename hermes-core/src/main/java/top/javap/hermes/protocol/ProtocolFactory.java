package top.javap.hermes.protocol;

import top.javap.hermes.protocol.impl.HermesProtocol;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
public final class ProtocolFactory {

    private static final ConcurrentMap<String, Protocol> CACHE = new ConcurrentHashMap<>();

    static {
        CACHE.put("hermes", new HermesProtocol());
    }

    public static Protocol getProtocol(String protocol) {
        return CACHE.get(protocol);
    }
}