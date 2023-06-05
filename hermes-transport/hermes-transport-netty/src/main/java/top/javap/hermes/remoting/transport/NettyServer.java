package top.javap.hermes.remoting.transport;

import io.netty.bootstrap.ServerBootstrap;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/28
 **/
public class NettyServer implements Server {

    private final ServerBootstrap bootstrap;
    private final String host;
    private final int port;

    public NettyServer(ServerBootstrap bootstrap, String host, int port) {
        this.bootstrap = bootstrap;
        this.host = host;
        this.port = port;
    }

    @Override
    public String host() {
        return host;
    }

    @Override
    public int port() {
        return port;
    }

    @Override
    public void close() {
        // todo
    }
}