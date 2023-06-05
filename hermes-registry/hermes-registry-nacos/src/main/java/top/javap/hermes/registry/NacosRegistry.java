package top.javap.hermes.registry;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.javap.hermes.common.Properties;
import top.javap.hermes.constant.DictConstant;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
public class NacosRegistry implements Registry {
    private static final Logger log = LoggerFactory.getLogger(NacosRegistry.class);

    private final NamingService namingService;

    public NacosRegistry(RegistryConfig config) {
        try {
            this.namingService = NacosFactory.createNamingService(config.getHost() + ":" + config.getPort());
        } catch (NacosException e) {
            log.error("namingService create error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void register(Properties properties) {
        Instance instance = new Instance();
        instance.setServiceName(properties.getAppName());
        instance.setClusterName(properties.getAppName());
        instance.setIp(properties.getHost());
        instance.setPort(properties.getPort());
        instance.setMetadata(properties.getMetadata());
        try {
            namingService.registerInstance(instance.getServiceName(), instance);
        } catch (NacosException e) {
            log.error("service register error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unregister() {
        // todo
    }

    @Override
    public void subscribe(String serviceName, NotifyListener listener) {
        try {
            namingService.subscribe(serviceName, new EventListener() {
                @Override
                public void onEvent(Event event) {
                    if (event instanceof NamingEvent) {
                        NamingEvent namingEvent = (NamingEvent) event;
                        List<Properties> propertiesList = namingEvent.getInstances().stream().map(i -> {
                            Properties properties = new Properties();
                            properties.put(DictConstant.HOST, i.getIp());
                            properties.put(DictConstant.PORT, i.getPort());
                            properties.put(DictConstant.PROTOCOL, i.getMetadata().get(DictConstant.PROTOCOL));
                            return properties;
                        }).collect(Collectors.toList());
                        listener.notify(propertiesList);
                    }
                }
            });
        } catch (NacosException e) {
            log.error("service subscribe error", e);
            throw new RuntimeException(e);
        }
    }
}