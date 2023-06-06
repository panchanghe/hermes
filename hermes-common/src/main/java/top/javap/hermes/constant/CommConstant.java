package top.javap.hermes.constant;

/**
 * @Author: pch
 * @Date: 2023/5/18 16:19
 * @Description:
 */
public interface CommConstant {

    String DEFAULT_SERIALIZATION = "top.javap.hermes.serialize.kryo.KryoSerialization";
    String DEFAULT_TRANSPORTER = "top.javap.hermes.remoting.transport.NettyTransporter";
    String DEFAULT_REGISTRY = "top.javap.hermes.registry.NacosRegistry";
    String DEFAULT_CLUSTER = "failfast";
    String DEFAULT_LOAD_BALANCE = "random";

    // 应用状态
    Integer APPLICATION_STATUS_CREATE = 0;
    Integer APPLICATION_STATUS_RUNNING = 1;
    Integer APPLICATION_STATUS_DESTROY = 2;

    // 线程模型相关
    int DEFAULT_ACCEPT_THREADS = 1;
    int DEFAULT_IO_THREADS = Math.min(Runtime.getRuntime().availableProcessors() + 1, 32);
    int DEFAULT_BIZ_THREADS = 200;
    int DEFAULT_KEEPALIVE_SECONDS = 0;
    int DEFAULT_QUEUES = 0;

    String DEFAULT_PROTOCOL = "hermes";
    int DEFAULT_PORT = 20430;
}