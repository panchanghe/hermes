package top.javap.hermes.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import top.javap.hermes.config.ExecutorConfig;
import top.javap.hermes.registry.RegistryConfig;
import top.javap.hermes.remoting.transport.TransporterConfig;

import java.util.LinkedHashSet;
import java.util.Set;

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
    @NestedConfigurationProperty
    private Scan scan;
    @NestedConfigurationProperty
    private RegistryConfig registryConfig = new RegistryConfig();
    @NestedConfigurationProperty
    private TransporterConfig transporterConfig = new TransporterConfig();
    @NestedConfigurationProperty
    private ExecutorConfig executorConfig = new ExecutorConfig();

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

    public Scan getScan() {
        return scan;
    }

    public void setScan(Scan scan) {
        this.scan = scan;
    }

    public RegistryConfig getRegistryConfig() {
        return registryConfig;
    }

    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }

    public TransporterConfig getTransporterConfig() {
        return transporterConfig;
    }

    public void setTransporterConfig(TransporterConfig transporterConfig) {
        this.transporterConfig = transporterConfig;
    }

    public ExecutorConfig getExecutorConfig() {
        return executorConfig;
    }

    public void setExecutorConfig(ExecutorConfig executorConfig) {
        this.executorConfig = executorConfig;
    }

    static class Scan {
        private Set<String> basePackages = new LinkedHashSet();

        public Set<String> getBasePackages() {
            return basePackages;
        }

        public void setBasePackages(Set<String> basePackages) {
            this.basePackages = basePackages;
        }
    }
}