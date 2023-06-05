package top.javap.hermes.protocol;

import io.netty.channel.ChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.javap.hermes.application.ApplicationConfig;
import top.javap.hermes.common.RpcContext;
import top.javap.hermes.constant.MessageConstant;
import top.javap.hermes.enums.InvokeMode;
import top.javap.hermes.exception.RpcException;
import top.javap.hermes.invoke.Futures;
import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.invoke.Result;
import top.javap.hermes.invoke.remote.HermesInvoker;
import top.javap.hermes.remoting.MessageHandler;
import top.javap.hermes.remoting.message.Invocation;
import top.javap.hermes.remoting.message.Request;
import top.javap.hermes.remoting.message.Response;
import top.javap.hermes.remoting.message.pool.MessagePoolUtil;
import top.javap.hermes.remoting.transport.Channel;
import top.javap.hermes.remoting.transport.Client;
import top.javap.hermes.remoting.transport.Server;
import top.javap.hermes.remoting.transport.Transporter;
import top.javap.hermes.util.InvocationUtil;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/17
 **/
public class HermesProtocol extends AbstractProtocol {
    private final Logger log = LoggerFactory.getLogger(HermesProtocol.class);

    @Override
    protected Server createServer(ApplicationConfig config) {
        return config.getTransporter().bind(config.getHost(), config.getPort(),
                new RequestHandler(config.getExecutorService()));
    }

    @Override
    protected Client createClient(String host, int port, Transporter transporter) {
        return transporter.connect(host, port, new ResponseHandler());
    }

    @Override
    protected Invoker doRefer(Client client) {
        return new HermesInvoker(client);
    }

    @ChannelHandler.Sharable
    private class ResponseHandler implements MessageHandler<Response> {

        @Override
        public void received(Channel channel, Response response) {
            if (response.getStatus() == MessageConstant.RES_STATUS_OK) {
                Futures.complete(response.getRequestId(), response.getBody());
            } else {
                Futures.completeException(response.getRequestId(), new RpcException((String) response.getBody()));
            }
            MessagePoolUtil.release(response);
        }
    }


    @ChannelHandler.Sharable
    private class RequestHandler implements MessageHandler<Request> {

        private final ExecutorService executorService;

        private RequestHandler(ExecutorService executorService) {
            this.executorService = executorService;
        }

        @Override
        public void received(Channel channel, Request request) {
            executorService.submit(() -> {
                Invocation invocation = (Invocation) request.getBody();
                InvocationUtil.recover(invocation);
                RpcContext.getAttachments().putAll(invocation.attachments());
                Invoker invoker = INVOKER_CACHE.get(invocation.key());
                if (Objects.isNull(invoker)) {
                    channel.send(Response.failed(request.getRequestId(), new RpcException("not found service,please check the key")));
                    return;
                }
                final Result result = invoker.invoke(invocation);
                final InvokeMode invokeMode = invocation.invokeMode();
                if (InvokeMode.SYNC.equals(invokeMode)) {
                    if (result.hasException()) {
                        channel.send(Response.failed(request.getRequestId(), result.getException()));
                    } else {
                        channel.send(Response.ok(request.getRequestId(), result.getValue()));
                    }
                } else if (InvokeMode.ASYNC.equals(invokeMode)) {
                    if (result.hasException()) {
                        channel.send(Response.failed(request.getRequestId(), result.getException()));
                    } else {
                        result.future().whenComplete((r, e) -> {
                            if (Objects.nonNull(e)) {
                                channel.send(Response.failed(request.getRequestId(), e));
                            } else {
                                channel.send(Response.ok(request.getRequestId(), r));
                            }
                        });
                    }
                } else {
                    // oneway
                }
                MessagePoolUtil.release(request);
            });
        }
    }
}