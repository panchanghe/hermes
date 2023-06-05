package top.javap.hermes.remoting.transport;

/**
 * @Author: pch
 * @Date: 2023/5/28 14:10
 * @Description:
 */
public interface Server {

    String host();

    int port();

    void close();
}