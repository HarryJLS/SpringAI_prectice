# Spring AI Practice - API使用指南

## 项目概述

这是一个基于Spring Boot 3.5.4的DDD架构项目，实现了时间服务和虚拟线程测试功能，具备完善的全局异常处理和统一返回结构。

## 核心功能

### 1. 统一返回结构

所有API接口都使用统一的 `Result<T>` 结构返回数据：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {...},
  "timestamp": 1703123456789,
  "path": "/api/time"
}
```

#### 状态码说明
- `200`: 成功
- `400`: 参数错误
- `401`: 未授权
- `403`: 禁止访问
- `404`: 资源未找到
- `405`: 方法不允许
- `408`: 请求超时
- `500`: 服务器内部错误

### 2. 全局异常处理

实现了完善的全局异常处理机制，涵盖以下异常类型：

- **业务异常**: 自定义业务逻辑异常
- **参数校验异常**: `@Valid`、`@Min`、`@Max` 等校验失败
- **参数绑定异常**: 参数类型转换失败
- **HTTP异常**: 404、405、消息不可读等
- **异步任务异常**: 线程中断、执行异常、超时等
- **系统异常**: 空指针、运行时异常等

### 3. 对象转换

使用类似MapStruct的映射器模式进行Domain对象与DTO对象之间的转换：

```java
// 使用方式
TimeInfoDto dto = TimeInfoMapper.INSTANCE.toDto(domainObject);
List<ThreadInfoDto> dtos = ThreadInfoMapper.INSTANCE.toDtoList(domainList);
```

## API接口

### 1. 获取当前时间

**接口地址**: `GET /api/time`

**返回示例**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "currentTime": "2024-01-01 12:00:00",
    "timestamp": 1703123456789
  },
  "timestamp": 1703123456789,
  "path": "/api/time"
}
```

### 2. 虚拟线程测试

**接口地址**: `GET /api/virtual-thread`

**参数**:
- `threadCount`: 线程数量 (默认5, 范围1-100)

**返回示例**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "threadIndex": 0,
      "threadName": "VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1",
      "executionTime": "2024-01-01 12:00:01.123"
    },
    {
      "threadIndex": 1,
      "threadName": "VirtualThread[#22]/runnable@ForkJoinPool-1-worker-2",
      "executionTime": "2024-01-01 12:00:02.234"
    }
  ],
  "timestamp": 1703123456789,
  "path": "/api/virtual-thread"
}
```

### 3. 异常测试接口

**接口地址**: `GET /api/test-exception`

**参数**:
- `type`: 异常类型 (business, runtime, null, illegal)

**用途**: 测试全局异常处理器的不同异常类型处理

## 异常返回示例

### 参数校验失败
```json
{
  "code": 400,
  "message": "参数校验失败: threadCount 线程数量不能大于100; ",
  "data": null,
  "timestamp": 1703123456789,
  "path": "/api/virtual-thread"
}
```

### 业务异常
```json
{
  "code": 400,
  "message": "这是一个测试业务异常",
  "data": null,
  "timestamp": 1703123456789,
  "path": "/api/test-exception"
}
```

### 404错误
```json
{
  "code": 404,
  "message": "接口不存在",
  "data": null,
  "timestamp": 1703123456789,
  "path": "/api/non-existent"
}
```

### 方法不允许
```json
{
  "code": 405,
  "message": "不支持的请求方法: POST，支持的方法: GET",
  "data": null,
  "timestamp": 1703123456789,
  "path": "/api/time"
}
```

## DDD架构层次

```
interfaces/     # 接口层 - 控制器、DTO、映射器、异常处理
├── controller/ # REST控制器
├── dto/        # 数据传输对象
├── mapper/     # 对象映射器
├── exception/  # 异常处理
└── common/     # 通用组件

application/    # 应用层 - 应用服务、流程编排
├── service/    # 应用服务
└── config/     # 配置类

domain/         # 领域层 - 领域模型、领域服务
├── model/      # 值对象
├── service/    # 领域服务
└── constant/   # 常量

infrastructure/ # 基础设施层 (预留)

bootstrap/      # 启动模块
```

## 启动应用

```bash
# 编译项目
mvn clean compile

# 启动应用
mvn spring-boot:run -pl bootstrap
```

应用启动后访问 `http://localhost:8080/api/time` 测试接口。

## 测试建议

1. **正常场景测试**:
   - 获取当前时间
   - 虚拟线程测试（不同线程数量）

2. **异常场景测试**:
   - 参数超出范围 (`threadCount=101`)
   - 参数类型错误 (`threadCount=abc`)
   - 不存在的接口 (`/api/non-existent`)
   - 不支持的HTTP方法 (`POST /api/time`)

3. **异常类型测试**:
   - 业务异常 (`/api/test-exception?type=business`)
   - 运行时异常 (`/api/test-exception?type=runtime`)
   - 空指针异常 (`/api/test-exception?type=null`)
   - 参数异常 (`/api/test-exception?type=illegal`)

## 特性总结

✅ **统一返回结构**: 所有接口返回相同格式的Result对象  
✅ **全局异常处理**: 完善的异常捕获和统一错误返回  
✅ **对象转换**: 类MapStruct风格的对象映射  
✅ **参数校验**: 支持JSR-303验证注解  
✅ **DDD架构**: 清晰的分层架构和依赖关系  
✅ **虚拟线程**: Java 21虚拟线程的实际应用  
✅ **异步处理**: CompletableFuture异步任务处理
