package top.javap.hermes.config;

/**
 * @Author: pch
 * @Date: 2023/5/17 19:22
 * @Description:
 */
public interface ReferenceConfig<T> {

    void setApplicationName(String applicationName);

    String getApplicationName();

    Class<T> getInterfaceClass();

    void setGroup(String group);

    String getGroup();

    void setVersion(String version);

    String getVersion();

    void set(T ref);

    T get();
}