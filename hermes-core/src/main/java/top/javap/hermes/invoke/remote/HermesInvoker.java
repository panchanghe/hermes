package top.javap.hermes.invoke.remote;

import top.javap.hermes.constant.MessageConstant;
import top.javap.hermes.enums.InvokeMode;
import top.javap.hermes.exception.RpcException;
import top.javap.hermes.invoke.*;
import top.javap.hermes.remoting.message.Invocation;
import top.javap.hermes.remoting.message.Request;
import top.javap.hermes.remoting.message.pool.MessagePoolUtil;
import top.javap.hermes.remoting.transport.Client;

import java.util.concurrent.CompletableFuture;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/27
 **/
public class HermesInvoker implements Invoker {

    private final Client client;

    public HermesInvoker(Client client) {
        this.client = client;
    }

    @Override
    public Result invoke(Invocation invocation) {
        Request request = MessagePoolUtil.getRequest();
        request.setOneWay(InvokeMode.ONEWAY.equals(invocation.invokeMode()));
        request.setEventType(MessageConstant.FLAG_EVENT_BIZ);
        request.setBody(invocation);
        client.send(request);
        final InvokeMode invokeMode = invocation.invokeMode();
        if (InvokeMode.SYNC.equals(invokeMode)) {
            return getSyncResult(Futures.newFuture(request.getRequestId()));
        } else if (InvokeMode.ASYNC.equals(invokeMode)) {
            return new AsyncResult(Futures.newFuture(request.getRequestId()));
        } else {
            // oneway
        }
        return EmptyResult.INSTANCE;
    }

    private Result getSyncResult(CompletableFuture<Object> future) {
        try {
            return SimpleResult.success(future.get());
        } catch (Exception e) {
            throw new RpcException(e);
        }
    }
}