package top.javap.hermes.metadata;

import top.javap.hermes.enums.InvokeMode;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/23
 **/
public class MethodMetadata {

    private final Method method;
    private InvokeMode invokeMode;
    private int retries = 0;
    private long timeout = -1L;
    private int key;

    public MethodMetadata(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public InvokeMode getInvokeMode() {
        return invokeMode;
    }

    public void setInvokeMode(InvokeMode invokeMode) {
        this.invokeMode = invokeMode;
    }

    public boolean isOneway() {
        return Objects.equals(InvokeMode.ONEWAY, invokeMode);
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "MethodMetadata{" +
                "method=" + method +
                ", invokeMode=" + invokeMode +
                ", retries=" + retries +
                ", timeout=" + timeout +
                ", key=" + key +
                '}';
    }
}