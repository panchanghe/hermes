package top.javap.hermes.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/17
 **/
public class DefaultReferenceConfig<T> implements ReferenceConfig<T> {
    private static final Logger log = LoggerFactory.getLogger(DefaultReferenceConfig.class);

    private String applicationName;
    private final Class<T> interfaceClass;
    private T ref;
    private String group;
    private String version;

    public DefaultReferenceConfig(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @Override
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public String getApplicationName() {
        return applicationName;
    }

    @Override
    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    @Override
    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void set(T ref) {
        this.ref = ref;
    }

    @Override
    public T get() {
        return ref;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultReferenceConfig<?> that = (DefaultReferenceConfig<?>) o;

        if (applicationName != null ? !applicationName.equals(that.applicationName) : that.applicationName != null)
            return false;
        if (interfaceClass != null ? !interfaceClass.equals(that.interfaceClass) : that.interfaceClass != null)
            return false;
        if (ref != null ? !ref.equals(that.ref) : that.ref != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = applicationName != null ? applicationName.hashCode() : 0;
        result = 31 * result + (interfaceClass != null ? interfaceClass.hashCode() : 0);
        result = 31 * result + (ref != null ? ref.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}