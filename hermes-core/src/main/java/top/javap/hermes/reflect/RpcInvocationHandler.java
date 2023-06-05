package top.javap.hermes.reflect;

import top.javap.hermes.common.RpcContext;
import top.javap.hermes.config.ReferenceConfig;
import top.javap.hermes.exception.RpcException;
import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.invoke.MethodMetadataManager;
import top.javap.hermes.invoke.Result;
import top.javap.hermes.metadata.MethodMetadata;
import top.javap.hermes.remoting.message.RpcInvocation;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/29
 **/
public class RpcInvocationHandler implements InvocationHandler {

    private final ReferenceConfig referenceConfig;
    private final Invoker invoker;
    private final MethodHandles.Lookup methodLookup;

    public RpcInvocationHandler(ReferenceConfig referenceConfig, Invoker invoker) {
        this.referenceConfig = referenceConfig;
        this.invoker = invoker;
        methodLookup = DefaultMethodLookup.lookup(referenceConfig.getInterfaceClass());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class == method.getDeclaringClass()) {
            return method.invoke(this, args);
        }
        if (method.isDefault()) {
            return invokeDefaultMethod(proxy, method, args);
        }
        final MethodMetadata methodMetadata = MethodMetadataManager.getMetadata(method);
        final RpcInvocation invocation = new RpcInvocation();
        invocation.setServiceName(referenceConfig.getInterfaceClass().getName());
        invocation.setGroup(referenceConfig.getGroup());
        invocation.setVersion(referenceConfig.getVersion());
        invocation.setMethod(method);
        invocation.setInvokeMode(methodMetadata.getInvokeMode());
        invocation.setArguments(args);
        invocation.attachments().putAll(RpcContext.getAttachments());
        try {
            final Result result = invoker.invoke(invocation);
            return result.recreate();
        } catch (Exception e) {
            throw new RpcException(e);
        } finally {
            RpcContext.clearAttachments();
        }
    }

    private Object invokeDefaultMethod(Object proxy, Method method, Object[] args) {
        try {
            MethodHandle methodHandle = methodLookup.findSpecial(referenceConfig.getInterfaceClass(), method.getName(), MethodType.methodType(method.getReturnType(), method.getParameterTypes()), referenceConfig.getInterfaceClass());
            return methodHandle.bindTo(proxy).invokeWithArguments(args);
        } catch (Throwable e) {
            throw new RuntimeException("The default method fails to execute", e);
        }
    }
}