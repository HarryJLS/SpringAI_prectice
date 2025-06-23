# DDD项目架构示例

基于Spring Boot 3.2.3和JDK 21的DDD（领域驱动设计）架构项目示例。

## 项目结构

项目采用模块化的DDD架构，各个模块通过Maven管理依赖关系：

- **domain**：领域层，包含业务实体、聚合根、领域事件、值对象和领域服务
- **application**：应用层，包含应用服务、命令处理器、事件订阅者等
- **infrastructure**：基础设施层，包含仓储实现、外部服务集成、消息队列等
- **interfaces**：接口层，包含REST API控制器、DTO转换器等
- **bootstrap**：启动层，应用程序入口和配置

## 依赖关系

```
bootstrap -> interfaces -> application -> domain
       \        /            /
        \      /            /
         \    /            /
          \  /            /
     infrastructure -----/
```

## 技术栈

- Java 21
- Spring Boot 3.2.3
- Spring Data JPA
- H2数据库 (开发环境)
- Lombok
- Spring Doc OpenAPI

## 开发环境设置

### 前提条件

- JDK 21
- Maven 3.8+

### 构建项目

```bash
mvn clean install
```

### 运行项目

```bash
cd bootstrap
mvn spring-boot:run
```

或者从IDE运行`Application.java`主类。

## API文档

运行应用后，可以通过以下URL访问API文档：

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI定义: http://localhost:8080/v3/api-docs

## H2控制台

运行应用后，可以通过以下URL访问H2数据库控制台：

- H2控制台: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - 用户名: `sa`
  - 密码: `password` 