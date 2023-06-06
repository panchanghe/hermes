package top.javap.hermes.remoting.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.javap.hermes.remoting.MessageHandler;
import top.javap.hermes.remoting.codec.ClientCodec;
import top.javap.hermes.remoting.codec.ServerCodec;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
public class NettyTransporter implements Transporter {
    private static final Logger log = LoggerFactory.getLogger(NettyTransporter.class);

    private final TransporterConfig config;

    public NettyTransporter(TransporterConfig config) {
        this.config = config;
    }

    @Override
    public <Request> Server bind(String host, int port, MessageHandler<Request> handler) {
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(new NioEventLoopGroup(config.getAcceptThreads()), new NioEventLoopGroup(config.getIoThreads()))
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, config.isTcpNoDelay())
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new ServerCodec());
                        sc.pipeline().addLast(new SimpleChannelInboundHandler<Request>() {

                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
                                handler.received(new NettyChannel(ctx), request);
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                ctx.channel().close();
                                log.error("channel handler exception", cause);
                            }
                        });
                    }
                });
        try {
            bootstrap.bind(host, port).sync();
        } catch (Exception e) {
            log.error("netty server port fails to be bound", e);
            throw new RuntimeException(e);
        }
        log.info("netty server started " + host + ":" + port);
        return new NettyServer(bootstrap, host, port);
    }

    @Override
    public <Response> Client connect(String host, int port, MessageHandler<Response> handler) {
        Bootstrap bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup(1))
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new ClientCodec());
                        sc.pipeline().addLast(new SimpleChannelInboundHandler<Response>() {

                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, Response response) throws Exception {
                                handler.received(new NettyChannel(ctx), response);
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                ctx.channel().close();
                                log.error("channel handler exception", cause);
                            }
                        });
                    }
                });
        try {
            return new NettyClient(bootstrap.connect(host, port).sync().channel());
        } catch (Exception e) {
            log.error("netty client connection failed", e);
            throw new RuntimeException(e);
        }
    }
}