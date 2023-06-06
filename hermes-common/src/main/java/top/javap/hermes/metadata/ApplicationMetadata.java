package top.javap.hermes.metadata;

import java.util.List;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/6
 **/
public class ApplicationMetadata {

    private String applicationName;
    private List<ServiceMetadata> services;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public List<ServiceMetadata> getServices() {
        return services;
    }

    public void setServices(List<ServiceMetadata> services) {
        this.services = services;
    }

    public static class ServiceMetadata {

        private String serviceName;
        private List<MethodMetadata> methods;

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public List<MethodMetadata> getMethods() {
            return methods;
        }

        public void setMethods(List<MethodMetadata> methods) {
            this.methods = methods;
        }
    }

    public static class MethodMetadata {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}