package top.javap.hermes.remoting;

import top.javap.hermes.remoting.transport.Channel;

/**
 * @Author: pch
 * @Date: 2023/5/28 14:19
 * @Description:
 */
public interface MessageHandler<T> {

    void received(Channel channel, T message);
}