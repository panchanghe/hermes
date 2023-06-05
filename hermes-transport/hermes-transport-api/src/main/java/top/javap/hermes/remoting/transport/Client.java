package top.javap.hermes.remoting.transport;

/**
 * @Author: pch
 * @Date: 2023/5/28 14:10
 * @Description:
 */
public interface Client {

    void send(Object message);

    void close();
}