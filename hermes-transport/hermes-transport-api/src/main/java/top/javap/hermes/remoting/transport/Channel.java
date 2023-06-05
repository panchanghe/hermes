package top.javap.hermes.remoting.transport;

/**
 * @Author: pch
 * @Date: 2023/5/28 15:00
 * @Description:
 */
public interface Channel {

    void send(Object message);

    void close();
}