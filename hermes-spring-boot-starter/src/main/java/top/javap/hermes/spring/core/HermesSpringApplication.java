package top.javap.hermes.spring.core;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import top.javap.hermes.annotation.ApplyScope;
import top.javap.hermes.application.Application;
import top.javap.hermes.application.ApplicationConfig;
import top.javap.hermes.cluster.Router;
import top.javap.hermes.config.DefaultServiceConfig;
import top.javap.hermes.config.ServiceConfig;
import top.javap.hermes.enums.Scope;
import top.javap.hermes.interceptor.Interceptor;
import top.javap.hermes.spring.annotation.HermesService;
import top.javap.hermes.spring.config.HermesConfigurationProperties;
import top.javap.hermes.util.SortUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/2
 **/
public class HermesSpringApplication extends Application {

    private final ApplicationContext context;
    private final AtomicBoolean initialized = new AtomicBoolean();

    @Autowired
    public HermesSpringApplication(ApplicationContext context) {
        this.context = context;
    }

    private void initialize() {
        if (initialized.compareAndSet(false, true)) {
            initApplicationConfig();
            initServices();
            initReferences();
            initRouters();
            initIntercepts();
        }
    }

    @Override
    public void start() {
        this.initialize();
        super.start();
        InvokerDelegateManager.referenceConfigSet().forEach(r -> {
            InvokerDelegateManager.setInvoker(r, appInvokers.get(r.getApplicationName()));
        });
    }

    private void initRouters() {
        List<Router> routers = Lists.newArrayList(context.getBeansOfType(Router.class).values());
        SortUtil.sort(routers);
        getRouterChain().addRouter(routers);
    }

    private void initReferences() {
        setReferences(InvokerDelegateManager.referenceConfigSet().stream().collect(Collectors.toList()));
    }

    private void initIntercepts() {
        final List<Interceptor> consumerInterceptors = new ArrayList<>();
        final List<Interceptor> providerInterceptors = new ArrayList<>();
        Collection<Interceptor> interceptors = context.getBeansOfType(Interceptor.class).values();
        interceptors.forEach(i -> {
            Scope scope = Scope.ALL;
            ApplyScope applyScope = i.getClass().getAnnotation(ApplyScope.class);
            if (Objects.nonNull(applyScope)) {
                scope = applyScope.scope();
            }
            if (Scope.ALL.equals(scope)) {
                consumerInterceptors.add(i);
                providerInterceptors.add(i);
            } else if (Scope.CONSUMER.equals(scope)) {
                consumerInterceptors.add(i);
            } else if (Scope.PROVIDER.equals(scope)) {
                providerInterceptors.add(i);
            }
        });
        SortUtil.sort(consumerInterceptors);
        SortUtil.sort(providerInterceptors);
        super.setConsumerInterceptors(consumerInterceptors);
        super.setProviderInterceptors(providerInterceptors);
    }

    private void initServices() {
        List<ServiceConfig> serviceConfigs = context.getBeansWithAnnotation(HermesService.class).values().stream()
                .map(s -> {
                    HermesService hermesService = s.getClass().getAnnotation(HermesService.class);
                    ServiceConfig serviceConfig = new DefaultServiceConfig(s.getClass().getInterfaces()[0]);
                    serviceConfig.setGroup(hermesService.group());
                    serviceConfig.setVersion(hermesService.version());
                    serviceConfig.setRef(s);
                    return serviceConfig;
                }).collect(Collectors.toList());
        setServices(serviceConfigs);
    }

    private void initApplicationConfig() {
        HermesConfigurationProperties properties = context.getBean(HermesConfigurationProperties.class);
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setApplicationName(properties.getApplicationName());
        if (StringUtils.isNotEmpty(properties.getProtocol())) {
            applicationConfig.setProtocol(properties.getProtocol());
        }
        if (StringUtils.isNotEmpty(properties.getHost())) {
            applicationConfig.setHost(properties.getHost());
        }
        if (Objects.nonNull(properties.getPort())) {
            applicationConfig.setPort(properties.getPort());
        }
        if (StringUtils.isNotEmpty(properties.getCluster())) {
            applicationConfig.setCluster(properties.getCluster());
        }
        if (StringUtils.isNotEmpty(properties.getLoadBalance())) {
            applicationConfig.setLoadBalance(properties.getLoadBalance());
        }
        applicationConfig.setRegistryConfig(properties.getRegistryConfig());
        applicationConfig.setTransporterConfig(properties.getTransporterConfig());
        applicationConfig.setExecutorConfig(properties.getExecutorConfig());
        super.setApplicationConfig(applicationConfig);
    }
}