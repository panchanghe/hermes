package top.javap.hermes.spring.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.javap.hermes.spring.core.ApplicationStartedListener;
import top.javap.hermes.spring.core.ReferenceBeanPostProcessor;
import top.javap.hermes.spring.core.ServiceRegistryPostProcessor;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/13
 **/
@ConditionalOnProperty(
        prefix = "hermes",
        name = {"enabled"},
        matchIfMissing = true
)
@Configuration
@EnableConfigurationProperties(HermesConfigurationProperties.class)
public class HermesAutoConfiguration {

    @Bean
    public ServiceRegistryPostProcessor serviceRegistryPostProcessor() {
        return new ServiceRegistryPostProcessor();
    }

    @Bean
    public ReferenceBeanPostProcessor referenceBeanPostProcessor() {
        return new ReferenceBeanPostProcessor();
    }

    @Bean
    public ApplicationStartedListener applicationStartedListener() {
        return new ApplicationStartedListener();
    }
}