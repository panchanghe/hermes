package top.javap.example;

import com.google.common.collect.Lists;
import top.javap.example.service.UserService;
import top.javap.hermes.application.Application;
import top.javap.hermes.application.ApplicationConfig;
import top.javap.hermes.config.DefaultReferenceConfig;
import top.javap.hermes.config.ReferenceConfig;
import top.javap.hermes.registry.RegistryConfig;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/7
 **/
public class SimpleConsumer {
    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig();
        config.setApplicationName("app-example");
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setHost("127.0.0.1");
        registryConfig.setPort(8848);
        config.setRegistryConfig(registryConfig);

        Application application = new Application();
        application.setApplicationConfig(config);

        ReferenceConfig referenceConfig = new DefaultReferenceConfig(UserService.class);
        referenceConfig.setApplicationName("app-example");
        referenceConfig.setGroup("g1");
        referenceConfig.setVersion("v1");
        application.setReferences(Lists.newArrayList(referenceConfig));
        application.start();
        UserService userService = (UserService) application.refer(referenceConfig);
        System.err.println(userService.get("aa"));
    }
}