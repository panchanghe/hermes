package top.javap.hermes.spring.core;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import top.javap.hermes.annotation.Intercept;
import top.javap.hermes.application.Application;
import top.javap.hermes.application.ApplicationConfig;
import top.javap.hermes.config.DefaultServiceConfig;
import top.javap.hermes.config.ServiceConfig;
import top.javap.hermes.enums.Scope;
import top.javap.hermes.interceptor.Interceptor;
import top.javap.hermes.registry.RegistryConfig;
import top.javap.hermes.spring.annotation.HermesService;
import top.javap.hermes.spring.config.HermesConfigurationProperties;

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

    private void initReferences() {
        setReferences(InvokerDelegateManager.referenceConfigSet().stream().collect(Collectors.toList()));
    }

    private void initIntercepts() {
        Collection<Interceptor> interceptors = context.getBeansOfType(Interceptor.class).values();
        interceptors.forEach(i -> {
            Scope scope = Scope.ALL;
            int order = 1;
            Intercept intercept = i.getClass().getAnnotation(Intercept.class);
            if (Objects.nonNull(intercept)) {
                scope = intercept.applyScope();
                order = intercept.order();
            }
            // todo order
            if (Scope.ALL.equals(scope)) {
                addConsumerInterceptor(i);
                addProviderInterceptor(i);
            } else if (Scope.CONSUMER.equals(scope)) {
                addConsumerInterceptor(i);
            } else if (Scope.PROVIDER.equals(scope)) {
                addProviderInterceptor(i);
            }
        });
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
        HermesConfigurationProperties configuration = context.getBean(HermesConfigurationProperties.class);
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setApplicationName(configuration.getApplicationName());
        if (StringUtils.isNotEmpty(configuration.getProtocol())) {
            applicationConfig.setProtocol(configuration.getProtocol());
        }
        if (StringUtils.isNotEmpty(configuration.getHost())) {
            applicationConfig.setHost(configuration.getHost());
        }
        if (Objects.nonNull(configuration.getPort())) {
            applicationConfig.setPort(configuration.getPort());
        }
        if (StringUtils.isNotEmpty(configuration.getCluster())) {
            applicationConfig.setCluster(configuration.getCluster());
        }
        if (StringUtils.isNotEmpty(configuration.getLoadBalance())) {
            applicationConfig.setLoadBalance(configuration.getLoadBalance());
        }
        if (Objects.nonNull(configuration.getAcceptThreads())) {
            applicationConfig.setAcceptThreads(configuration.getAcceptThreads());
        }
        if (Objects.nonNull(configuration.getIoThreads())) {
            applicationConfig.setIoThreads(configuration.getIoThreads());
        }
        if (Objects.nonNull(configuration.getBizThreads())) {
            applicationConfig.setBizThreads(configuration.getBizThreads());
        }
        if (Objects.nonNull(configuration.getQueues())) {
            applicationConfig.setQueues(configuration.getQueues());
        }
        if (StringUtils.isNotEmpty(configuration.getTransporterClass())) {
            applicationConfig.setTransporterClass(configuration.getTransporterClass());
        }
        applicationConfig.setRegistryConfig(new RegistryConfig(configuration.getRegistryAddress()));
        super.setApplicationConfig(applicationConfig);
    }
}