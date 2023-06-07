package top.javap.example;

import com.google.common.collect.Lists;
import top.javap.example.service.UserService;
import top.javap.example.service.impl.UserServiceImpl;
import top.javap.hermes.application.Application;
import top.javap.hermes.application.ApplicationConfig;
import top.javap.hermes.config.DefaultReferenceConfig;
import top.javap.hermes.config.DefaultServiceConfig;
import top.javap.hermes.config.ReferenceConfig;
import top.javap.hermes.config.ServiceConfig;
import top.javap.hermes.registry.RegistryConfig;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/7
 **/
public class SimpleProvider {
    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig();
        config.setApplicationName("app-example");
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setHost("127.0.0.1");
        registryConfig.setPort(8848);
        config.setRegistryConfig(registryConfig);

        Application application = new Application();
        application.setApplicationConfig(config);
        ServiceConfig serviceConfig = new DefaultServiceConfig(UserService.class);
        serviceConfig.setGroup("g1");
        serviceConfig.setVersion("v1");
        serviceConfig.setRef(new UserServiceImpl());
        application.setServices(Lists.newArrayList(serviceConfig));

        ReferenceConfig referenceConfig = new DefaultReferenceConfig(UserService.class);
        referenceConfig.setApplicationName("app-example");
        referenceConfig.setGroup("g1");
        referenceConfig.setVersion("v1");
        application.setReferences(Lists.newArrayList(referenceConfig));
        application.start();
    }
}