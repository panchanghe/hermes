package top.javap.hermes.application;

import top.javap.hermes.cluster.Cluster;
import top.javap.hermes.cluster.ClusterFactory;
import top.javap.hermes.cluster.loadbalance.LoadBalanceFactory;
import top.javap.hermes.cluster.support.RegistryServiceList;
import top.javap.hermes.common.Properties;
import top.javap.hermes.config.ReferenceConfig;
import top.javap.hermes.config.ServiceConfig;
import top.javap.hermes.constant.CommConstant;
import top.javap.hermes.constant.DictConstant;
import top.javap.hermes.interceptor.Interceptor;
import top.javap.hermes.interceptor.InterceptorInvoker;
import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.protocol.ProtocolFactory;
import top.javap.hermes.proxy.ProxyFactory;
import top.javap.hermes.registry.RegistryFactory;
import top.javap.hermes.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/17
 **/
public class Application {

    private ApplicationConfig applicationConfig;
    private List<ServiceConfig> services = new ArrayList<>();
    private List<ReferenceConfig> references = new ArrayList<>();
    private Set<String> referenceApplications = new HashSet<>();
    protected Map<String, Invoker> appInvokers = new HashMap<>();
    private final List<Interceptor> providerInterceptors = new ArrayList<>();
    private final List<Interceptor> consumerInterceptors = new ArrayList<>();
    private final AtomicInteger status = new AtomicInteger(CommConstant.APPLICATION_STATUS_CREATE);

    public Application() {
    }

    public Application(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public List<ServiceConfig> getServices() {
        return services;
    }

    public void setServices(List<ServiceConfig> services) {
        this.services = services;
    }

    public List<ReferenceConfig> getReferences() {
        return references;
    }

    public void setReferences(List<ReferenceConfig> references) {
        this.references = references;
    }

    public void start() {
        if (status.compareAndSet(CommConstant.APPLICATION_STATUS_CREATE, CommConstant.APPLICATION_STATUS_RUNNING)) {
            init();
            exportService();
            referService();
        }
    }

    public void stop() {
        // todo
    }

    private void init() {
        referenceApplications = references.stream()
                .map(ReferenceConfig::getApplicationName)
                .collect(Collectors.toSet());
    }

    public <T> T refer(ReferenceConfig<T> referenceConfig) {
        if (Objects.isNull(referenceConfig.get())) {
            synchronized (referenceConfig) {
                if (Objects.isNull(referenceConfig.get())) {
                    doRefer(referenceConfig);
                }
            }
        }
        return referenceConfig.get();
    }

    private <T> void doRefer(ReferenceConfig<T> referenceConfig) {
        Invoker invoker = appInvokers.get(referenceConfig.getApplicationName());
        Assert.notNull(invoker, "invoker is null");
        T proxy = ProxyFactory.getProxy(invoker, referenceConfig);
        referenceConfig.set(proxy);
    }

    private Invoker applyConsumerInterceptor(Invoker invoker) {
        return applyInterceptor(invoker, consumerInterceptors);
    }

    public Invoker applyProviderInterceptor(Invoker invoker) {
        return applyInterceptor(invoker, providerInterceptors);
    }

    private Invoker applyInterceptor(Invoker invoker, List<Interceptor> interceptors) {
        ListIterator<Interceptor> iterator = interceptors.listIterator(interceptors.size());
        while (iterator.hasPrevious()) {
            invoker = new InterceptorInvoker(invoker, iterator.previous());
        }
        return invoker;
    }

    private String referenceKey(Class type, String group, String version) {
        return type.getName() + ":" + group + ":" + version;
    }

    private String referenceKey(ReferenceConfig referenceConfig) {
        return referenceConfig.getInterfaceClass().getName() + ":" + referenceConfig.getGroup() + ":" + referenceConfig.getVersion();
    }

    private void exportService() {
        ProtocolFactory.getProtocol(applicationConfig.getProtocol()).export(this);
        RegistryFactory.getRegistry(applicationConfig.getRegistryConfig()).register(buildRegistryProperties());
    }

    private void referService() {
        referenceApplications.forEach(app -> {
            Cluster cluster = ClusterFactory.get(applicationConfig.getCluster());
            Invoker invoker = cluster.aggregation(new RegistryServiceList(RegistryFactory.getRegistry(applicationConfig.getRegistryConfig()),
                            app, applicationConfig.getTransporter()),
                    LoadBalanceFactory.get(applicationConfig.getLoadBalance()));
            invoker = applyConsumerInterceptor(invoker);
            appInvokers.put(app, invoker);
        });
    }

    private Properties buildRegistryProperties() {
        Properties properties = new Properties();
        properties.put(DictConstant.APPLICATION_NAME, applicationConfig.getApplicationName());
        properties.put(DictConstant.HOST, applicationConfig.getHost());
        properties.put(DictConstant.PORT, applicationConfig.getPort());
        properties.getMetadata().put(DictConstant.PROTOCOL, applicationConfig.getProtocol());
        return properties;
    }

    public void addConsumerInterceptor(Interceptor interceptor) {
        this.consumerInterceptors.add(interceptor);
    }

    public void addProviderInterceptor(Interceptor interceptor) {
        this.providerInterceptors.add(interceptor);
    }
}