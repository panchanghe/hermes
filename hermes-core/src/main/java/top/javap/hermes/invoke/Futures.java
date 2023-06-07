package top.javap.hermes.invoke;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/27
 **/
public class Futures {

    private static final ConcurrentMap<Integer, CompletableFuture> FUTURE_CACHE = new ConcurrentHashMap<>();

    public static <T> CompletableFuture<T> newFuture(Integer id) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        FUTURE_CACHE.put(id, completableFuture);
        return completableFuture;
    }

    public static void complete(Integer id, Object result) {
        CompletableFuture future = FUTURE_CACHE.remove(id);
        if (Objects.nonNull(future)) {
            future.complete(result);
        }
    }

    public static void completeException(Integer id, Throwable t) {
        CompletableFuture future = FUTURE_CACHE.remove(id);
        if (Objects.nonNull(future)) {
            future.completeExceptionally(t);
        }
    }
}