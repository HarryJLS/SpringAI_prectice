#!/bin/bash

# API测试脚本
# 测试全局异常处理和对象转换功能

BASE_URL="http://localhost:8080/api"

echo "=== API功能测试 ==="
echo

# 1. 测试获取当前时间
echo "1. 测试获取当前时间："
curl -s "$BASE_URL/time" | jq .
echo
echo

# 2. 测试虚拟线程（正常情况）
echo "2. 测试虚拟线程（正常情况，3个线程）："
curl -s "$BASE_URL/virtual-thread?threadCount=3" | jq .
echo
echo

# 3. 测试参数校验异常（线程数量超出范围）
echo "3. 测试参数校验异常（线程数量超出范围）："
curl -s "$BASE_URL/virtual-thread?threadCount=101" | jq .
echo
echo

# 4. 测试参数类型错误
echo "4. 测试参数类型错误："
curl -s "$BASE_URL/virtual-thread?threadCount=abc" | jq .
echo
echo

# 5. 测试业务异常
echo "5. 测试业务异常："
curl -s "$BASE_URL/test-exception?type=business" | jq .
echo
echo

# 6. 测试运行时异常
echo "6. 测试运行时异常："
curl -s "$BASE_URL/test-exception?type=runtime" | jq .
echo
echo

# 7. 测试空指针异常
echo "7. 测试空指针异常："
curl -s "$BASE_URL/test-exception?type=null" | jq .
echo
echo

# 8. 测试参数异常
echo "8. 测试参数异常："
curl -s "$BASE_URL/test-exception?type=illegal" | jq .
echo
echo

# 9. 测试404异常
echo "9. 测试404异常："
curl -s "$BASE_URL/non-existent-endpoint" | jq .
echo
echo

# 10. 测试HTTP方法不支持
echo "10. 测试HTTP方法不支持："
curl -s -X POST "$BASE_URL/time" | jq .
echo
echo

echo "=== 测试完成 ==="
