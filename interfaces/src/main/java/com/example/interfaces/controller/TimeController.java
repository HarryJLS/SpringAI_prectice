package com.example.interfaces.controller;

import com.example.application.dto.ThreadInfoDto;
import com.example.application.dto.TimeInfoDto;
import com.example.application.service.TimeApplicationService;
import com.example.domain.exception.BusinessException;
import com.example.interfaces.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;

/**
 * 时间相关接口控制器
 * 提供时间查询和虚拟线程测试的REST接口
 * 直接调用Application层服务，获取DTO对象
 * 
 * @author Claude
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api")
@Validated
public class TimeController {

    /**
     * 时间应用服务
     */
    @Autowired
    private TimeApplicationService timeApplicationService;

    /**
     * 获取当前时间接口
     * 
     * @return 统一响应结果，包含当前时间和时间戳信息
     */
    @GetMapping("/time")
    public Result<TimeInfoDto> getCurrentTime() {
        try {
            TimeInfoDto timeInfoDto = timeApplicationService.getCurrentTime();
            return Result.success(timeInfoDto);
        } catch (Exception e) {
            throw BusinessException.threadError("获取当前时间失败", e);
        }
    }

    /**
     * 虚拟线程测试接口
     * 创建指定数量的虚拟线程，每个线程执行不同时长的模拟工作
     * 并阻塞等待所有线程完成后返回结果
     * 
     * @param threadCount 要创建的线程数量，默认为5，范围1-100
     * @return 统一响应结果，包含所有线程的执行信息列表
     */
    @GetMapping("/virtual-thread")
    public Result<List<ThreadInfoDto>> virtualThreadTest(
            @RequestParam(defaultValue = "5") 
            @Min(value = 1, message = "线程数量不能小于1")
            @Max(value = 100, message = "线程数量不能大于100") 
            int threadCount) {
        
        try {
            List<ThreadInfoDto> threadInfoDtos = timeApplicationService.executeVirtualThreadTasks(threadCount);
            return Result.success(threadInfoDtos);
        } catch (Exception e) {
            throw BusinessException.threadError("虚拟线程测试执行失败", e);
        }
    }
    
    /**
     * 测试异常处理接口
     * 用于测试全局异常处理器的功能
     * 
     * @param type 异常类型：business, runtime, null, illegal
     * @return 不会正常返回，会抛出异常被全局异常处理器捕获
     */
    @GetMapping("/test-exception")
    public Result<String> testException(@RequestParam(defaultValue = "business") String type) {
        switch (type.toLowerCase()) {
            case "business":
                throw BusinessException.paramError("这是一个测试业务异常");
            case "runtime":
                throw new RuntimeException("这是一个测试运行时异常");
            case "null":
                String nullStr = null;
                @SuppressWarnings("null")
                String result = nullStr.toString(); // 故意触发NullPointerException
                return Result.success(result);
            case "illegal":
                throw new IllegalArgumentException("这是一个测试参数异常");
            default:
                throw new RuntimeException("未知的异常类型: " + type);
        }
    }
}