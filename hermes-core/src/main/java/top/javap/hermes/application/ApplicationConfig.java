package top.javap.hermes.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.javap.hermes.common.Properties;
import top.javap.hermes.constant.CommConstant;
import top.javap.hermes.constant.DictConstant;
import top.javap.hermes.registry.RegistryConfig;
import top.javap.hermes.remoting.transport.Transporter;
import top.javap.hermes.util.NetUtil;

import java.util.Objects;
import java.util.concurrent.*;

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

    // 注册中心
    private RegistryConfig registryConfig;

    // 线程池相关
    private int acceptThreads = CommConstant.DEFAULT_ACCEPT_THREADS;
    private int ioThreads = CommConstant.DEFAULT_IO_THREADS;
    private int bizThreads = CommConstant.DEFAULT_BIZ_THREADS;
    private int queues = CommConstant.DEFAULT_QUEUES;
    private ExecutorService executorService;

    // 网络传输相关
    private String transporterClass = CommConstant.DEFAULT_TRANSPORTER;
    private Transporter transporter;

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

    public int getAcceptThreads() {
        return acceptThreads;
    }

    public void setAcceptThreads(int acceptThreads) {
        this.acceptThreads = acceptThreads;
    }

    public int getIoThreads() {
        return ioThreads;
    }

    public void setIoThreads(int ioThreads) {
        this.ioThreads = ioThreads;
    }

    public int getBizThreads() {
        return bizThreads;
    }

    public void setBizThreads(int bizThreads) {
        this.bizThreads = bizThreads;
    }

    public int getQueues() {
        return queues;
    }

    public void setQueues(int queues) {
        this.queues = queues;
    }

    public RegistryConfig getRegistryConfig() {
        return registryConfig;
    }

    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }

    public void setTransporterClass(String transporterClass) {
        this.transporterClass = transporterClass;
    }

    public ExecutorService getExecutorService() {
        if (Objects.isNull(executorService)) {
            synchronized (this) {
                if (Objects.isNull(executorService)) {
                    executorService = new ThreadPoolExecutor(bizThreads, bizThreads, 0L, TimeUnit.MILLISECONDS,
                            queues <= 0 ? new SynchronousQueue<Runnable>() : new LinkedBlockingQueue<Runnable>(queues));
                }
            }
        }
        return executorService;
    }

    public Transporter getTransporter() {
        if (Objects.isNull(transporter)) {
            synchronized (this) {
                if (Objects.isNull(transporter)) {
                    Properties properties = new Properties();
                    properties.put(DictConstant.ACCTPE_THREADS, acceptThreads);
                    properties.put(DictConstant.IO_THREADS, ioThreads);
                    try {
                        transporter = (Transporter) Class.forName(transporterClass)
                                .getConstructor(Properties.class).newInstance(properties);
                    } catch (Exception e) {
                        log.error("not found transporter " + transporterClass, e);
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return transporter;
    }
}