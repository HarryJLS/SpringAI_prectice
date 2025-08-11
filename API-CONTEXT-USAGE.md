# AI 聊天接口使用说明

## 统一规范

所有聊天接口现在都使用相同的请求和响应格式，确保API的一致性。

## 接口列表

### 1. 无状态聊天接口

**接口地址：** `POST /api/ai/chat`

**功能：** 进行无状态的AI聊天，不保存任何上下文信息，每次请求都是独立的。

**请求示例：**
```json
{
    "message": "你好，请介绍一下Spring Boot"
}
```

**响应示例：**
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "sessionId": null,
        "message": "Spring Boot是一个开源的Java框架...",
        "timestamp": "2024-01-15T10:30:00",
        "contextUsed": false
    }
}
```

### 2. 上下文聊天接口

**接口地址：** `POST /api/ai/chat/context`

**功能：** 进行有状态的AI聊天，支持会话级别的上下文记忆。

**请求示例：**
```json
{
    "sessionId": "chat_abc123",
    "message": "你还记得我刚才问的问题吗？",
    "enableContext": true
}
```

**响应示例：**
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "sessionId": "chat_abc123",
        "message": "是的，您刚才问的是关于Spring Boot的问题...",
        "timestamp": "2024-01-15T10:31:00",
        "contextUsed": true
    }
}
```

### 3. 清除会话上下文

**接口地址：** `DELETE /api/ai/chat/context/{sessionId}`

**功能：** 清除指定会话的所有上下文信息。

**响应示例：**
```json
{
    "code": 200,
    "message": "success",
    "data": "会话上下文已清除"
}
```

### 4. 查看会话信息

**接口地址：** `GET /api/ai/chat/context/{sessionId}/info`

**功能：** 获取指定会话中保存的消息数量。

**响应示例：**
```json
{
    "code": 200,
    "message": "success",
    "data": 6
}
```

## 请求参数说明

### ChatRequestDto

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| sessionId | String | 否 | 会话ID，不传会自动生成（仅上下文接口使用） |
| message | String | 是 | 用户输入的消息内容，最大1000字符 |
| enableContext | Boolean | 否 | 是否启用上下文，默认true（仅上下文接口使用） |

### ChatResponseDto

| 字段名 | 类型 | 说明 |
|--------|------|------|
| sessionId | String | 会话ID，无状态聊天为null |
| message | String | AI回复的消息内容 |
| timestamp | LocalDateTime | 消息创建时间 |
| contextUsed | Boolean | 是否使用了上下文 |

## 使用场景

### 无状态聊天
- 适用于单次问答
- 每次请求都是独立的
- 不需要维护会话状态
- 性能更好

### 有状态聊天
- 适用于连续对话
- 支持多轮问答
- 可以引用之前的对话内容
- 需要管理会话生命周期

## 注意事项

1. **会话管理：** 上下文信息目前存储在内存中，应用重启后会丢失
2. **性能考虑：** 每个会话最多保留最近10轮对话历史
3. **安全性：** 请确保sessionId的安全性，避免会话劫持
4. **清理策略：** 建议定期清理不活跃的会话以释放内存

## 测试脚本

运行以下命令测试所有接口：

```bash
./test-context-api.sh
```
