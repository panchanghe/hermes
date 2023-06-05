package top.javap.hermes.spring.core;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import top.javap.hermes.spring.annotation.HermesService;

import java.io.IOException;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/1
 **/
public class ClassPathServiceScanner extends ClassPathBeanDefinitionScanner {

    public ClassPathServiceScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public void registerFilters() {
        addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                return metadataReader.getAnnotationMetadata().hasAnnotation(HermesService.class.getName());
            }
        });
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().hasAnnotation(HermesService.class.getName());
    }
}