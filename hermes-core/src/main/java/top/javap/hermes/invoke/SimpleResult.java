package top.javap.hermes.invoke;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/23
 **/
public class SimpleResult implements Result, Serializable {
    private static final long serialVersionUID = 0L;

    private final Object result;
    private final Throwable throwable;

    public SimpleResult(Object result, Throwable throwable) {
        this.result = result;
        this.throwable = throwable;
    }

    @Override
    public Object getValue() {
        return result;
    }

    @Override
    public Exception getException() {
        return (Exception) throwable;
    }

    @Override
    public boolean hasException() {
        return Objects.nonNull(throwable);
    }

    @Override
    public <T> CompletableFuture<T> future() {
        if (result instanceof CompletableFuture) {
            return (CompletableFuture<T>) result;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Object recreate() {
        return getValue();
    }

    public static Result success(Object result) {
        return new SimpleResult(result, null);
    }

    public static Result error(Throwable throwable) {
        return new SimpleResult(null, throwable);
    }
}