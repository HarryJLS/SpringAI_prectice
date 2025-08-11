# DDD架构重构说明

## 重构概述

根据DDD（领域驱动设计）的最佳实践，我们对项目的包结构进行了重新组织，确保每一层都有明确的职责，依赖关系清晰。

## 重构前后对比

### 重构前的问题
- DTO、Mapper、异常处理都放在Interface层，职责混乱
- 业务异常与技术异常混合
- 层次依赖关系不清晰

### 重构后的架构

```
interfaces/               # 接口层 - 负责外部交互
├── controller/          # REST控制器
├── common/             # 统一返回结构
└── exception/          # 全局异常处理器（技术异常）

application/              # 应用层 - 负责业务流程编排
├── service/            # 应用服务
├── dto/               # 数据传输对象（从interfaces移入）
├── mapper/            # 对象映射器（从interfaces移入）
└── config/            # 配置类

domain/                   # 领域层 - 核心业务逻辑
├── model/             # 值对象、实体
├── service/           # 领域服务
├── exception/         # 业务异常（从interfaces移入）
└── constant/          # 常量

infrastructure/           # 基础设施层（预留）
└── ...

bootstrap/               # 启动模块
└── Application.java
```

## 关键改进

### 1. 业务异常归属调整
**之前**: `interfaces/exception/BusinessException.java`  
**现在**: `domain/exception/BusinessException.java`

**原因**: 业务异常表达业务规则的违反，属于领域层的概念。

### 2. DTO归属调整
**之前**: `interfaces/dto/`  
**现在**: `application/dto/`

**原因**: DTO定义了应用服务的输入输出契约，应该由Application层管理。

### 3. Mapper归属调整
**之前**: `interfaces/mapper/`  
**现在**: `application/mapper/`

**原因**: 映射器负责Domain对象与DTO之间的转换，这是Application层的职责。

### 4. 统一返回结构保留
**保留**: `interfaces/common/Result.java`

**原因**: Result是专门为HTTP API设计的响应格式，属于接口层关注点。

## 层次职责划分

### Interface层（接口层）
- **职责**: 处理HTTP请求/响应，数据格式转换，路由
- **包含**: Controller、Result、GlobalExceptionHandler
- **依赖**: Application层

### Application层（应用层）
- **职责**: 业务流程编排，事务管理，对象转换
- **包含**: ApplicationService、DTO、Mapper、Config
- **依赖**: Domain层

### Domain层（领域层）
- **职责**: 核心业务逻辑，业务规则，领域模型
- **包含**: Entity、ValueObject、DomainService、BusinessException
- **依赖**: 无（最内层）

### Infrastructure层（基础设施层）
- **职责**: 技术实现，数据持久化，外部服务调用
- **包含**: Repository实现、外部API客户端
- **依赖**: Domain层

## 依赖关系图

```
┌─────────────────┐
│   Interface     │ ──→ Application
│    Layer        │
└─────────────────┘
         │
         ▼
┌─────────────────┐
│   Application   │ ──→ Domain
│     Layer       │
└─────────────────┘
         │
         ▼
┌─────────────────┐
│    Domain       │
│     Layer       │ ◄── Infrastructure
└─────────────────┘     Layer
```

## 数据流转

### 请求流程
1. **Interface层**: 接收HTTP请求，参数校验
2. **Application层**: 调用应用服务，编排业务流程
3. **Domain层**: 执行核心业务逻辑
4. **Application层**: 将Domain对象转换为DTO
5. **Interface层**: 包装成Result统一返回

### 对象转换
```java
// Controller -> ApplicationService
TimeInfoDto dto = timeApplicationService.getCurrentTime();

// ApplicationService 内部
TimeInfo domain = timeService.getCurrentTimeInfo();  // Domain对象
TimeInfoDto dto = TimeInfoMapper.INSTANCE.toDto(domain);  // 转换为DTO
```

## 异常处理策略

### 业务异常（Domain层）
```java
// domain/exception/BusinessException.java
throw BusinessException.paramError("参数验证失败");
```

### 技术异常（Interface层）
```java
// interfaces/exception/GlobalExceptionHandler.java
@ExceptionHandler(BusinessException.class)
public Result<Object> handleBusinessException(BusinessException e) {
    return Result.error(e.getCode(), e.getMessage());
}
```

## 重构带来的好处

1. **职责分离**: 每层都有明确的职责边界
2. **依赖管理**: 清晰的依赖方向，便于测试和维护
3. **可扩展性**: 易于添加新功能和修改现有功能
4. **可测试性**: 每层可以独立进行单元测试
5. **代码复用**: Domain层的业务逻辑可以被多个Application服务复用

## 最佳实践

1. **严格遵循依赖方向**: 外层依赖内层，内层不依赖外层
2. **DTO只在边界使用**: DTO不应该渗透到Domain层
3. **业务逻辑集中**: 核心业务逻辑必须在Domain层
4. **接口层轻薄**: Interface层只处理技术关注点
5. **应用层编排**: Application层负责业务流程的编排

这样的架构设计符合DDD的核心理念，能够更好地应对复杂业务需求的变化。
