package top.javap.hermes.spring.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import top.javap.hermes.config.DefaultReferenceConfig;
import top.javap.hermes.config.ReferenceConfig;
import top.javap.hermes.invoke.InvokerDelegate;
import top.javap.hermes.proxy.ProxyFactory;
import top.javap.hermes.spring.annotation.HermesReference;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/1
 **/
public class ReferenceBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        InjectionMetadata metadata = this.findInjectionMetadata(bean.getClass());
        try {
            metadata.inject(bean, beanName, pvs);
            return pvs;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private InjectionMetadata findInjectionMetadata(Class<?> clazz) {
        Collection<InjectionMetadata.InjectedElement> elements = findInjectedElements(clazz);
        return new InjectionMetadata(clazz, elements);
    }

    private Collection<InjectionMetadata.InjectedElement> findInjectedElements(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(HermesReference.class))
                .map(f -> new AnnotatedFieldElement(f, null)).collect(Collectors.toList());
    }

    public class AnnotatedFieldElement extends InjectionMetadata.InjectedElement {
        private final ReferenceConfig referenceConfig;

        protected AnnotatedFieldElement(Member member, PropertyDescriptor pd) {
            super(member, pd);
            Field field = (Field) member;
            HermesReference hermesReference = field.getAnnotation(HermesReference.class);
            referenceConfig = new DefaultReferenceConfig(field.getType());
            referenceConfig.setApplicationName(hermesReference.application());
            referenceConfig.setGroup(hermesReference.group());
            referenceConfig.setVersion(hermesReference.version());
        }

        @Override
        protected Object getResourceToInject(Object target, String requestingBeanName) {
            InvokerDelegate invokerDelegate = new InvokerDelegate();
            InvokerDelegateManager.put(referenceConfig, invokerDelegate);
            // 先注入一个占位符
            return ProxyFactory.getProxy(invokerDelegate, referenceConfig);
        }
    }
}