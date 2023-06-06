# Hermes
Hermes是一个轻量级、高性能的RPC框架。它旨在简化开发流程、改善代码质量和提高开发效率，让调用远程服务像调用本地方法一样简单！
![Hermes Logo](https://s2.xptou.com/2023/06/02/6479dc23717bd.jpg)

## 特性
- 轻量级：代码库小，只包含最基本的功能
- 高性能：针对性能进行了优化
- 简单易用：采用简洁的API，方便快速上手
- 良好的扩展性：核心组件均支持扩展
- 良好的文档支持：提供完善的使用文档，为开发者提供便利
- 无缝整合SpringBoot

## 性能优化
- 高度紧凑且轻量的自定义通信协议
- 仅用4个字节来定位服务，反观dubbo则要传输一堆数据
- Kryo高性能序列化
- Netty高性能网络IO框架
- 序列化&反序列化时，直接从缓冲区读取/写入，减少内存拷贝的次数(TODO)

## 安装
请确保您已安装 `Maven`

1、单独使用
```bash
<dependency>
    <groupId>top.javap.hermes</groupId>
    <artifactId>hermes-core</artifactId>
    <version>${latest.version}</version>
</dependency>
```
2、在Spring Boot项目中使用
```bash
<dependency>
    <groupId>top.javap.hermes</groupId>
    <artifactId>hermes-spring-boot-starter</artifactId>
    <version>${latest.version}</version>
</dependency>
```

## 使用
1、单独使用
很多时候，一个应用可能既是服务提供者，又是服务消费者，Hermes均把它们封装在Application中。
```java
public static void main(String[] args) {
    ApplicationConfig config = new ApplicationConfig();
    config.setApplicationName("app-example");
    config.setRegistryConfig(new RegistryConfig("nacos://127.0.0.1:8848"));
    Application application = new Application();
    application.setApplicationConfig(config);
    ServiceConfig serviceConfig = new DefaultServiceConfig(UserService.class);
    serviceConfig.setGroup("g1");
    serviceConfig.setVersion("v1");
    serviceConfig.setRef(new UserServiceImpl());
    application.setServices(Lists.newArrayList(serviceConfig));

    ReferenceConfig referenceConfig = new DefaultReferenceConfig(UserService.class);
    referenceConfig.setApplicationName("app-example");
    referenceConfig.setGroup("g1");
    referenceConfig.setVersion("v1");
    application.setReferences(Lists.newArrayList(referenceConfig));
    application.start();
}
```
获取服务，调用即可。
```java
UserService userService = (UserService) application.refer(referenceConfig);
User user = userService.get("hermes");
System.err.println(user);
```

2、在Spring Boot中使用
在`application.yaml`对Hermes进行配置，下面是一份完整配置，实际上绝大多数配置都有默认值，你可以对配置进行简化。
```yaml
hermes:
  applicationName: fx-user
  protocol: hermes
  port: 20430
  cluster: failfast
  loadBalance: random
  scan:
    base-packages: top.javap.example.service
  registry-config:
    host: 127.0.0.1
    port: 8848
  transporter-config:
    accept-threads: 2
    io-threads: 10
    tcp-no-delay: false
  executor-config:
    core-pool-size: 10
    maximum-pool-size: 100
    keep-alive-seconds: 60
    queues: 1000
```

给需要暴露的服务加上`@HermesService`注解
```java
@HermesService(group = "g1", version = "v1")
public class UserServiceImpl implements UserService {

    public User get(String name) {
        User user = new User();
        user.setName(name);
        user.setAge(18);
        return user;
    }
}
```

给需要引用的服务加上`@HermesReference`注解即可调用服务
```java
@HermesReference(application = "fx-user", group = "g1", version = "v1")
private UserService userService;
```

### 服务路由
服务路由接口是：`top.javap.hermes.cluster.Router`，通过路由可以轻松实现灰度发布、蓝绿发布等能力。
1、实现路由并交给Spring管理
```java
@Component
public class MyRouter implements Router {
    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public List<Invoker> route(List<Invoker> invokers, Invocation invocation) {
        // your rule
        return invokers;
    }
}
```

### 拦截器
Hermes拦截器接口是：`top.javap.hermes.interceptor.Interceptor`，拦截器可以同时应用于提供者和消费者，也可只应用于一方。默认应用于两端，可通过`@ApplyScope`注解配置。

1、实现拦截器并交给Spring管理
```java
@Component
@ApplyScope(scope = Scope.CONSUMER)
public class MyInterceptor implements Interceptor {
    @Override
    public Result intercept(Invoker invoker, Invocation invocation) {
        // before....
        Result result = invoker.invoke(invocation);
        // after....
        return result;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
```

### 调用方式
Hermes支持三种调用方式
#### SYNC
默认的调用方式，即同步调用，当前线程会阻塞等待接口响应
```java
public interface UserService {
    User get(String name);
}
```

#### ASYNC
异步调用，方法调用会立马返回，后续通过`CompletableFuture`对象获取结果。
```java
public interface UserService {
    CompletableFuture<User> getByAsync(String name);
}
```

#### ONEWAY
Hermes还支持一种性能极高的调用方式：ONEWAY，即单向调用，客户端不期望服务端返回响应结果，只管调用，性能极高，适用于对数据安全不敏感的场景，例如记录日志。
> 返回类型只能是void
```java
public interface UserService {
    @RpcMethod(oneway = true)
    void oneway(String name);
}
```

## 贡献
我们欢迎任何形式的贡献！无论是提交 bug 报告、建议、修改源码，还是编写文档。

## 版权说明

本项目基于 [MIT](LICENSE) 协议发布，详细版权说明请查看我们的 [许可文件](LICENSE)。

## 联系信息和社区支持

* [项目主页](https://github.com/panchanghe/hermes)
* [问题追踪](https://github.com/panchanghe/hermes/issues)
* [提交代码](https://github.com/panchanghe/hermes)

对于任何问题或建议，请在 [Issues](https://github.com/panchanghe/hermes/issues) 中提交。同时也可以在我们的社区讨论、学习和分享相关经验故事。

## 致谢
感谢所有为 [Hermes](https://github.com/panchanghe/hermes) 做出贡献的朋友们！