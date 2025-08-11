# API 接口统一规范修改说明

## 修改概览

将 `/api/ai/chat` 接口从 GET 请求改为 POST 请求，使其与上下文聊天接口保持统一的规范。

## 修改前后对比

### 修改前

**请求方式：** `GET /api/ai/chat`

**请求参数：**
```
GET /api/ai/chat?message=你好
```

**响应格式：**
```json
{
    "code": 200,
    "message": "操作成功",
    "data": "你好！我是Qwen，是阿里巴巴集团旗下的通义实验室..."
}
```

### 修改后

**请求方式：** `POST /api/ai/chat`

**请求参数：**
```json
{
    "message": "你好"
}
```

**响应格式：**
```json
{
    "code": 200,
    "message": "操作成功",
    "data": {
        "sessionId": null,
        "message": "你好！我是Qwen，是阿里巴巴集团旗下的通义实验室...",
        "timestamp": "2025-08-11T15:41:02.924432",
        "contextUsed": false
    }
}
```

## 统一规范的优势

### 1. 一致的请求格式
- 所有聊天接口都使用 POST 请求
- 都使用 `@RequestBody` 接收 `ChatRequestDto`
- 统一的参数验证规则

### 2. 一致的响应格式
- 都返回 `ChatResponseDto` 对象
- 包含统一的字段：sessionId、message、timestamp、contextUsed
- 便于前端统一处理

### 3. 更好的扩展性
- 未来可以轻松添加新的请求参数
- 响应格式固定，便于版本兼容

### 4. 类型安全
- 使用强类型的 DTO 而不是原始字符串
- 编译时可以检查类型错误

## 迁移指南

### 前端调用代码修改

**修改前：**
```javascript
// GET 请求
const response = await fetch('/api/ai/chat?message=' + encodeURIComponent(userMessage));
const data = await response.json();
const aiMessage = data.data; // 直接是字符串
```

**修改后：**
```javascript
// POST 请求
const response = await fetch('/api/ai/chat', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        message: userMessage
    })
});
const data = await response.json();
const aiMessage = data.data.message; // 从对象中获取
const isContextUsed = data.data.contextUsed; // 可以知道是否使用了上下文
```

### 接口选择建议

- **无状态聊天：** 使用 `POST /api/ai/chat`
  - 适用于独立的问答
  - 每次请求都是全新的对话

- **有状态聊天：** 使用 `POST /api/ai/chat/context`
  - 适用于连续对话
  - 需要维护对话上下文

## 测试验证

运行测试脚本验证所有接口正常工作：

```bash
./test-context-api.sh
```

## 总结

通过这次统一规范的修改，我们实现了：

✅ 统一的 API 设计模式  
✅ 一致的请求/响应格式  
✅ 更好的类型安全性  
✅ 便于前端统一处理  
✅ 为未来扩展做好准备  

这样的设计使得 API 更加专业、规范，提升了整体的开发体验。
