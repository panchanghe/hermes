package top.javap.hermes.registry;

import top.javap.hermes.constant.CommConstant;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/17
 **/
public class RegistryConfig {

    private String className = CommConstant.DEFAULT_REGISTRY;

    private String host;

    private int port;

    private String dataId;

    private String group;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}