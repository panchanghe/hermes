package top.javap.hermes.util;

import top.javap.hermes.exception.RpcException;

import java.util.Collection;
import java.util.Objects;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
public final class Assert {

    public static void notNull(Object obj, String msg) {
        if (Objects.isNull(obj)) {
            throw new RpcException(msg);
        }
    }

    public static void equals(Object a, Object b, String msg) {
        if (!Objects.equals(a, b)) {
            throw new RpcException(msg);
        }
    }

    public static void notEmpty(Collection collection, String msg) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw new RpcException(msg);
        }
    }

    public static void notEmpty(String s, String msg) {
        if (Objects.isNull(s) || s.isEmpty()) {
            throw new RpcException(msg);
        }
    }
}