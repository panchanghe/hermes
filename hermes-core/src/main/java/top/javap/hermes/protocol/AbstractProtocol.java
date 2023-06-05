package top.javap.hermes.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.javap.hermes.application.Application;
import top.javap.hermes.application.ApplicationConfig;
import top.javap.hermes.common.Properties;
import top.javap.hermes.config.ServiceConfig;
import top.javap.hermes.constant.DictConstant;
import top.javap.hermes.invoke.Invoker;
import top.javap.hermes.invoke.MethodMetadataManager;
import top.javap.hermes.invoke.local.ReflectionInvoker;
import top.javap.hermes.metadata.MethodMetadata;
import top.javap.hermes.remoting.transport.Client;
import top.javap.hermes.remoting.transport.Server;
import top.javap.hermes.remoting.transport.Transporter;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/18
 **/
public abstract class AbstractProtocol implements Protocol {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractProtocol.class);
    protected static final ConcurrentMap<String, Server> SERVER_CACHE = new ConcurrentHashMap<>();
    protected static final ConcurrentMap<String, Client> CLIENT_CACHE = new ConcurrentHashMap<>();
    protected final ConcurrentMap<Integer, Invoker> INVOKER_CACHE = new ConcurrentHashMap<>();

    @Override
    public void export(Application application) {
        doExport(application);
        openServer(application.getApplicationConfig());
    }

    @Override
    public Invoker refer(Properties properties) {
        Client client = openClient(properties.getHost(), properties.getPort(), (Transporter) properties.get(DictConstant.TRANSPORT));
        return doRefer(client);
    }

    protected abstract Invoker doRefer(Client client);

    private Client openClient(String host, int port, Transporter transporter) {
        final String key = host + port;
        Client client = CLIENT_CACHE.get(key);
        if (Objects.isNull(client)) {
            synchronized (this) {
                client = CLIENT_CACHE.get(key);
                if (Objects.isNull(client)) { // double check
                    CLIENT_CACHE.put(key, client = createClient(host, port, transporter));
                }
            }
        }
        return client;
    }

    private <T> Invoker wrapperInvoker(Object service, Method method) {
        return new ReflectionInvoker(service, method);
    }

    protected final void openServer(ApplicationConfig config) {
        final String key = config.getHost() + config.getPort();
        Server server = SERVER_CACHE.get(key);
        if (Objects.isNull(server)) {
            synchronized (this) {
                server = SERVER_CACHE.get(key);
                if (Objects.isNull(server)) { // double check
                    SERVER_CACHE.put(key, server = createServer(config));
                }
            }
        }
    }

    protected abstract Server createServer(ApplicationConfig config);

    protected abstract Client createClient(String host, int port, Transporter transporter);

    protected <T> void doExport(Application application) {
        for (ServiceConfig<T> serviceConfig : application.getServices()) {
            for (Method method : serviceConfig.getInterfaceClass().getDeclaredMethods()) {
                MethodMetadata methodMetadata = MethodMetadataManager.register(serviceConfig, method);
                Invoker invoker = wrapperInvoker(serviceConfig.getRef(), method);
                invoker = application.applyProviderInterceptor(invoker);
                Invoker oldValue = INVOKER_CACHE.put(methodMetadata.getKey(), invoker);
                if (oldValue != null) {
                    throw new RuntimeException("service conflict:" + methodMetadata);
                }
            }
        }
    }
}