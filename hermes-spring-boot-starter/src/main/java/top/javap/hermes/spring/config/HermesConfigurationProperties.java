package top.javap.hermes.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/1
 **/
@ConfigurationProperties(prefix = "hermes")
public class HermesConfigurationProperties {

    private String applicationName;
    private String protocol;
    private String host;
    private Integer port;
    private String cluster;
    private String loadBalance;

    private Integer acceptThreads;
    private Integer ioThreads;
    private Integer bizThreads;
    private Integer queues;

    private String transporterClass;
    private String registryAddress;

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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
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

    public Integer getAcceptThreads() {
        return acceptThreads;
    }

    public void setAcceptThreads(Integer acceptThreads) {
        this.acceptThreads = acceptThreads;
    }

    public Integer getIoThreads() {
        return ioThreads;
    }

    public void setIoThreads(Integer ioThreads) {
        this.ioThreads = ioThreads;
    }

    public Integer getBizThreads() {
        return bizThreads;
    }

    public void setBizThreads(Integer bizThreads) {
        this.bizThreads = bizThreads;
    }

    public Integer getQueues() {
        return queues;
    }

    public void setQueues(Integer queues) {
        this.queues = queues;
    }

    public String getTransporterClass() {
        return transporterClass;
    }

    public void setTransporterClass(String transporterClass) {
        this.transporterClass = transporterClass;
    }

    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }
}