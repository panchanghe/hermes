package top.javap.hermes.spring.core;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import top.javap.hermes.application.Application;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/1
 **/
public class ApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        Application application = new HermesSpringApplication(applicationContext);
        application.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}