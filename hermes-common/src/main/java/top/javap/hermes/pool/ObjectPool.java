package top.javap.hermes.pool;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/1
 **/
public interface ObjectPool<T> {

    T get();

    void release(T object);
}