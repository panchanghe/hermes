package top.javap.hermes.metadata;

import top.javap.hermes.annotation.RpcMethod;
import top.javap.hermes.enums.InvokeMode;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/29
 **/
public class ReflectMethodMetadataParser implements MethodMetadataParser {

    @Override
    public MethodMetadata parse(Method method) {
        MethodMetadata metadata = new MethodMetadata(method);
        metadata.setInvokeMode(getInvokeMode(method));
        metadata.setRetries(getRetries(method));
        metadata.setTimeout(getRetries(method));
        return metadata;
    }

    private long getTimeout(Method method) {
        RpcMethod annotation = method.getAnnotation(RpcMethod.class);
        if (Objects.nonNull(annotation)) {
            return annotation.timeout();
        }
        return -1L;
    }

    private int getRetries(Method method) {
        RpcMethod annotation = method.getAnnotation(RpcMethod.class);
        if (Objects.nonNull(annotation)) {
            return annotation.retries();
        }
        return 0;
    }

    private InvokeMode getInvokeMode(Method method) {
        RpcMethod annotation = method.getAnnotation(RpcMethod.class);
        if (Objects.nonNull(annotation)) {
            if (annotation.oneway()) {
                return InvokeMode.ONEWAY;
            }
        }
        if (CompletableFuture.class.isAssignableFrom(method.getReturnType())) {
            return InvokeMode.ASYNC;
        }
        return InvokeMode.SYNC;
    }

    private boolean isAsync(Method method) {
        return CompletableFuture.class.isAssignableFrom(method.getReturnType());
    }
}