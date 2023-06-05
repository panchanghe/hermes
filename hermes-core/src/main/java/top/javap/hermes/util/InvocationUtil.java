package top.javap.hermes.util;

import top.javap.hermes.config.ServiceConfig;
import top.javap.hermes.invoke.MethodMetadataManager;
import top.javap.hermes.metadata.MethodMetadata;
import top.javap.hermes.remoting.message.Invocation;
import top.javap.hermes.remoting.message.RpcInvocation;

import java.util.Objects;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/23
 **/
public class InvocationUtil {

    public static void recover(Invocation inv) {
        if (inv instanceof RpcInvocation) {
            RpcInvocation invocation = (RpcInvocation) inv;
            MethodMetadata methodMetadata = MethodMetadataManager.getMethodMetadata(invocation.key());
            if (Objects.nonNull(methodMetadata)) {
                invocation.setMethod(methodMetadata.getMethod());
                invocation.setInvokeMode(methodMetadata.getInvokeMode());
                ServiceConfig serviceConfig = MethodMetadataManager.getServiceConfig(invocation.key());
                if (Objects.nonNull(serviceConfig)) {
                    invocation.setServiceName(serviceConfig.getName());
                    invocation.setGroup(serviceConfig.getGroup());
                    invocation.setVersion(serviceConfig.getVersion());
                }
            }
        }
    }
}