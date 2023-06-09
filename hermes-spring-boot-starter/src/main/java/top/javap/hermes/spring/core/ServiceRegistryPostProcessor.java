package top.javap.hermes.spring.core;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/5
 **/
public class ServiceRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private Environment environment;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        String scanPackages = environment.getProperty("hermes.scan.base-packages");
        if (StringUtils.isNotBlank(scanPackages)) {
            String[] packages = scanPackages.split(",");
            ClassPathServiceScanner scanner = new ClassPathServiceScanner(registry);
            scanner.registerFilters();
            scanner.scan(packages);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        // NOP
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}