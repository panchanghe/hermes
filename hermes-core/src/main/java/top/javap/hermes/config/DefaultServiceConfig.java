package top.javap.hermes.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/17
 **/
public class DefaultServiceConfig<T> implements ServiceConfig<T> {
    private static final Logger log = LoggerFactory.getLogger(DefaultServiceConfig.class);

    private final Class<T> interfaceClass;
    private T ref;
    private String group;
    private String version;

    public DefaultServiceConfig(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getName() {
        return interfaceClass.getName();
    }

    @Override
    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public void setRef(T ref) {
        this.ref = ref;
    }

    public T getRef() {
        return ref;
    }

    @Override
    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }
}