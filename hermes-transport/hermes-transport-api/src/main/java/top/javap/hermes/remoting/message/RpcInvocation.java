package top.javap.hermes.remoting.message;

import top.javap.hermes.enums.InvokeMode;
import top.javap.hermes.util.KeyUtil;
import top.javap.hermes.util.MethodUtil;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
public class RpcInvocation implements Invocation, Serializable {
    private static final long serialVersionUID = 0L;
    private static final Object[] EMPTY_ATGS = new Object[0];

    private Integer key;
    private String serviceName;
    private String group;
    private String version;
    private Method method;
    private InvokeMode invokeMode;
    private Object[] arguments = EMPTY_ATGS;
    private Map<String, Object> attachments = new HashMap<>();

    @Override
    public int key() {
        if (Objects.isNull(key)) {
            key = KeyUtil.methodKey(serviceName, group, version, MethodUtil.getMethodDescription(method));
        }
        return key;
    }

    @Override
    public String serviceName() {
        return serviceName;
    }

    @Override
    public String group() {
        return group;
    }

    @Override
    public String version() {
        return version;
    }

    @Override
    public Method method() {
        return method;
    }

    @Override
    public InvokeMode invokeMode() {
        return invokeMode;
    }

    public void setInvokeMode(InvokeMode invokeMode) {
        this.invokeMode = invokeMode;
    }

    @Override
    public Object[] arguments() {
        return arguments;
    }

    @Override
    public Map<String, Object> attachments() {
        return attachments;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public void setAttachments(Map<String, Object> attachments) {
        this.attachments = attachments;
    }

    public void setKey(Integer key) {
        this.key = key;
    }
}