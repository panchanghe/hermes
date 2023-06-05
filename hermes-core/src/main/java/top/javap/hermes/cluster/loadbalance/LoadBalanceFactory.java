package top.javap.hermes.cluster.loadbalance;

import top.javap.hermes.cluster.LoadBalance;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/28
 **/
public class LoadBalanceFactory {

    private static final ConcurrentMap<String, LoadBalance> CACHE = new ConcurrentHashMap<>();

    static {
        CACHE.put(RandomLoadBalance.NAME, new RandomLoadBalance());
    }

    public static LoadBalance get(String name) {
        return CACHE.get(name);
    }
}