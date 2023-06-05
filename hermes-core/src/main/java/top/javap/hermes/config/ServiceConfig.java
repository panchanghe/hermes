package top.javap.hermes.config;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/17
 **/
public interface ServiceConfig<T> {

    Class<T> getInterfaceClass();

    String getName();

    void setGroup(String group);

    String getGroup();

    void setVersion(String version);

    String getVersion();

    void setRef(T ref);

    T getRef();
}