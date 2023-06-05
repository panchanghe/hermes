package top.javap.hermes.remoting.transport;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/28
 **/
public class NettyChannel implements Channel {

    private final ChannelHandlerContext ctx;

    public NettyChannel(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void send(Object message) {
        ctx.writeAndFlush(message);
    }

    @Override
    public void close() {
        ctx.close();
    }
}