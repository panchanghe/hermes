package top.javap.hermes.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.javap.hermes.config.ExecutorConfig;
import top.javap.hermes.constant.CommConstant;
import top.javap.hermes.registry.Registry;
import top.javap.hermes.registry.RegistryConfig;
import top.javap.hermes.remoting.transport.Transporter;
import top.javap.hermes.remoting.transport.TransporterConfig;
import top.javap.hermes.util.NetUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/1
 **/
public class ApplicationConfig {
    private static final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

    private String applicationName;
    private String protocol = CommConstant.DEFAULT_PROTOCOL;
    private String host = NetUtil.getLocalHost();
    private int port = CommConstant.DEFAULT_PORT;
    private String cluster = CommConstant.DEFAULT_CLUSTER;
    private String loadBalance = CommConstant.DEFAULT_LOAD_BALANCE;

    // 注册中心层
    private RegistryConfig registryConfig = new RegistryConfig();
    // 传输层
    private TransporterConfig transporterConfig = new TransporterConfig();
    // 业务线程池
    private ExecutorConfig executorConfig = new ExecutorConfig();

    private Registry registry;
    private Transporter transporter;
    private ExecutorService executorService;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(String loadBalance) {
        this.loadBalance = loadBalance;
    }

    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }

    public TransporterConfig getTransporterConfig() {
        return transporterConfig;
    }

    public ExecutorConfig getExecutorConfig() {
        return executorConfig;
    }

    public Registry getRegistry() {
        return registry;
    }

    public Transporter getTransporter() {
        return transporter;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setTransporterConfig(TransporterConfig transporterConfig) {
        this.transporterConfig = transporterConfig;
    }

    public void setExecutorConfig(ExecutorConfig executorConfig) {
        this.executorConfig = executorConfig;
    }

    public void initialize() {
        registry = createRegistry(registryConfig);
        transporter = createTransporter(transporterConfig);
        executorService = createExecutorService(executorConfig);
    }

    private static Registry createRegistry(RegistryConfig config) {
        try {
            return (Registry) Class.forName(config.getClassName()).getConstructor(RegistryConfig.class).newInstance(config);
        } catch (Exception e) {
            log.error("registry instantiation error", e);
            throw new RuntimeException(e);
        }
    }

    private static ExecutorService createExecutorService(ExecutorConfig executorConfig) {
        return new ThreadPoolExecutor(executorConfig.getCorePoolSize(), executorConfig.getMaximumPoolSize(),
                executorConfig.getKeepAliveSeconds(), TimeUnit.SECONDS,
                executorConfig.getQueues() <= 0 ? new SynchronousQueue<Runnable>()
                        : new LinkedBlockingQueue<Runnable>(executorConfig.getQueues()));
    }

    private static Transporter createTransporter(TransporterConfig transporterConfig) {
        try {
            return (Transporter) Class.forName(transporterConfig.getClassName()).
                    getConstructor(TransporterConfig.class).newInstance(transporterConfig);
        } catch (Exception e) {
            log.error("not found transporter " + transporterConfig.getClassName(), e);
            throw new RuntimeException(e);
        }
    }
}