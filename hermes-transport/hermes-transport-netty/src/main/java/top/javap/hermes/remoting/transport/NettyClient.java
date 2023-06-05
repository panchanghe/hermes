package top.javap.hermes.remoting.transport;

import io.netty.channel.Channel;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/28
 **/
public class NettyClient implements Client {

    private final Channel channel;

    public NettyClient(Channel channel) {
        this.channel = channel;
    }


    @Override
    public void send(Object message) {
        channel.writeAndFlush(message);
    }

    @Override
    public void close() {
        channel.close();
    }
}