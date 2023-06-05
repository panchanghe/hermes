package top.javap.hermes.common;

import top.javap.hermes.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/28
 **/
public final class RpcContext {

    protected final Map<String, Object> attachments = new HashMap<>();

    private static final ThreadLocal<RpcContext> holder = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return new RpcContext();
        }
    };

    private RpcContext() {

    }

    public static RpcContext get() {
        return holder.get();
    }

    public static void setAttachment(String key, Object value) {
        Assert.notEmpty(key, "key cannot be empty");
        Assert.notNull(value, "value cannot be null");
        get().attachments.put(key, value);
    }

    public static Object getAttachment(String key) {
        Assert.notEmpty(key, "key cannot be empty");
        return get().attachments.get(key);
    }

    public static Map<String, Object> getAttachments() {
        return get().attachments;
    }

    public static void clearAttachments() {
        get().attachments.clear();
    }
}