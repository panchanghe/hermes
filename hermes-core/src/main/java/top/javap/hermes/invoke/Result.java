package top.javap.hermes.invoke;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: pch
 * @Date: 2023/5/18 12:34
 * @Description:
 */
public interface Result {

    Object getValue();

    Exception getException();

    boolean hasException();

    Object recreate();

    default <T> CompletableFuture<T> future() {
        throw new UnsupportedOperationException();
    }
}