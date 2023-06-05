# Hermes

Hermes是一个轻量级、高性能的RPC框架。它旨在简化开发流程、改善代码质量和提高开发效率，让调用远程服务像调用本地方法一样简单！

![Hermes Logo](https://s2.xptou.com/2023/06/02/6479dc23717bd.jpg)

## 特性

* 轻量级：代码库小，只包含最基本的功能
* 高性能：针对性能进行了优化，以便在不同的环境中实现高性能
* 简单易用：采用简洁的API，方便用户快速上手
* 良好的文档支持：提供完善的使用文档，为开发者提供便利
* 活跃的社区：积极参与的社区支持，代码库和文档得到持续更新和完善
* 无缝整合SpringBoot

## 安装

请确保您已安装 ``Maven``

1、单独使用
```bash
<dependency>
    <groupId>top.javap</groupId>
    <artifactId>hermes-core</artifactId>
    <version>${latest.version}</version>
</dependency>
```
2、在Spring Boot项目中使用
```bash
<dependency>
    <groupId>top.javap</groupId>
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
在`application.yaml`对Hermes进行配置
```yaml
hermes:
  enabled: true
  applicationName: fx-user
  port: 20430
  registryAddress: nacos://127.0.0.1:8848
  scan:
    base-package: org.example.service
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

### 拦截器
Hermes拦截器接口是：`top.javap.hermes.interceptor.Interceptor`，拦截器可以同时应用于提供者和消费者，也可只引用于一方。

1、手动注册拦截器
```java
application.addConsumerInterceptor(new MyInterceptor());
application.addProviderInterceptor(new MyInterceptor());
```

2、通过Spring注入拦截器
```java
@Intercept(
        applyScope = Scope.CONSUMER,
        order = 1
)
@Component
public class MyInterceptor implements Interceptor {

    public Result intercept(Invoker invoker, Invocation invocation) {
        // before...
        Result result = invoker.invoke(invocation);
        // after...
        return result;
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

* [项目主页](https://your_project_home_url)
* [问题追踪](https://your_issue_tracker_url)
* [提交代码](https://your_submit_code_url)

对于任何问题或建议，请在 [Issues](https://gitee.com/panchanghe/aurora/issues) 中提交。同时也可以在我们的社区讨论、学习和分享相关经验故事。

## 致谢
感谢所有为 [Aurora](https://gitee.com/panchanghe/aurora) 做出贡献的朋友们！