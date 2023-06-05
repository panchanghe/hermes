package top.javap.hermes.remoting.transport;

import top.javap.hermes.remoting.MessageHandler;

/**
 * @Author: pch
 * @Date: 2023/5/18 14:30
 * @Description:
 */
public interface Transporter {

    <T> Server bind(String host, int port, MessageHandler<T> handler);

    <T> Client connect(String host, int port, MessageHandler<T> handler);
}