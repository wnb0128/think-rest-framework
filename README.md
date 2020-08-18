# think-rest-framework
微服务快速开发脚手架

## 项目涉及服务组件
#### 1. nacos
Nacos是一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。Nacos 支持几乎所有主流类型的服务的发现、配置和管理:
* Kubernetes Service
* gRPC & Dubbo RPC Service
* Spring Cloud RESTful Service
官网：https://nacos.io/
github:https://github.com/alibaba/nacos/releases
```sh
sh startup.sh -m standalone
```
#### 2. sentinel-dashboard
github:https://github.com/alibaba/Sentinel
```sh
nohup java -Dserver.port=8718 -Dcsp.sentinel.dashboard.server=localhost:8718 -Dproject.name=sentinel-dashboard -Dcsp.sentinel.api.port=8719 -jar sentinel-dashboard-1.7.2.jar &
```

## 脚手架项目运行调试
### 添加配置中心
1. 打开nacos的管理中心(http://192.168.0.63:8848/nacos/#/login)
2. 新增data-id为${prefix}-${spring.profile.active}.${file-extension}
3. 执行Application.main()启动项目
* prefix 默认为 spring.application.name 的值，也可以通过配置项 spring.cloud.nacos.config.prefix来配置。
* spring.profile.active 即为当前环境对应的 profile，详情可以参考 Spring Boot文档。注意：当 spring.profile.active 为空时，对应的连接符 - 也将不存在，dataId 的拼接格式变成 prefix.{file-extension}
* file-extension 为配置内容的数据格式，可以通过配置项 spring.cloud.nacos.config.file-extension 来配置。目前只支持 properties 和 yaml 类型。
关于配置格式，通俗的讲，即为file-extension。如配置格式选择properties格式，则DataId后缀必须为properties（注意yaml，可写为yml，与yaml同义，但与bootstrap配置中的file-extension必须一致），配置内容也必须按照properties格式编写。
<pre>
<code>
server.port=8828
server.servlet.context-path=/${spring.application.name}
</code>
</pre>

### 修改网关服务网关配置
<pre>
<code>
#rest-admin-feign
spring.cloud.gateway.routes[0].id=rest-admin-feign
spring.cloud.gateway.routes[0].uri=lb://rest-admin-feign
spring.cloud.gateway.routes[0].predicates[0]=Path=/rest-admin-feign/**
spring.cloud.gateway.routes[0].filters[0]=StripPrefix=0
</code>
</pre>
