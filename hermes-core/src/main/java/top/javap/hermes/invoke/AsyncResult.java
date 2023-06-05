package top.javap.hermes.invoke;

import top.javap.hermes.exception.RpcException;

import java.util.concurrent.CompletableFuture;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/27
 **/
public class AsyncResult implements Result {

    private final CompletableFuture future;

    public AsyncResult(CompletableFuture future) {
        this.future = future;
    }

    @Override
    public Object getValue() {
        try {
            return future.get();
        } catch (Exception e) {
            throw new RpcException(e);
        }
    }

    @Override
    public Exception getException() {
        return null;
    }

    @Override
    public boolean hasException() {
        return future.isCompletedExceptionally();
    }

    @Override
    public <T> CompletableFuture<T> future() {
        return future;
    }

    @Override
    public Object recreate() {
        return future;
    }
}