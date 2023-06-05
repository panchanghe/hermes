package top.javap.hermes.cluster;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/28
 **/
public class ClusterFactory {

    private static final ConcurrentMap<String, Cluster> CACHE = new ConcurrentHashMap<>();

    static {
        CACHE.put(FailfastCluster.NAME, new FailfastCluster());
    }

    public static Cluster get(String name) {
        return CACHE.get(name);
    }
}