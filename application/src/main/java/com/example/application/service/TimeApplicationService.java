package com.example.application.service;

import com.example.application.dto.ThreadInfoDto;
import com.example.application.dto.TimeInfoDto;
import com.example.application.mapper.ThreadInfoMapper;
import com.example.application.mapper.TimeInfoMapper;
import com.example.domain.model.ThreadInfo;
import com.example.domain.model.TimeInfo;
import com.example.domain.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * 时间应用服务类
 * 负责编排时间相关的业务流程，协调Domain层服务
 * 负责Domain对象与DTO对象之间的转换
 * 
 * @author Claude
 * @since 1.0.0
 */
@Service
public class TimeApplicationService {
    
    /**
     * 时间领域服务
     */
    @Autowired
    private TimeService timeService;
    
    /**
     * 虚拟线程池执行器
     */
    @Autowired
    @Qualifier("virtualThreadPool")
    private ExecutorService virtualThreadPool;
    
    /**
     * 获取当前时间
     * 
     * @return 时间信息DTO对象
     */
    public TimeInfoDto getCurrentTime() {
        TimeInfo timeInfo = timeService.getCurrentTimeInfo();
        return TimeInfoMapper.INSTANCE.toDto(timeInfo);
    }
    
    /**
     * 执行虚拟线程任务
     * 创建指定数量的虚拟线程，每个线程执行模拟工作并返回线程信息
     * 使用CompletableFuture.get()阻塞等待所有线程执行完成
     * 
     * @param threadCount 要创建的线程数量
     * @return 包含所有线程执行结果的DTO对象列表
     */
    public List<ThreadInfoDto> executeVirtualThreadTasks(int threadCount) {
        List<CompletableFuture<ThreadInfo>> futures = new ArrayList<>();
        
        // 创建并提交虚拟线程任务
        for (int i = 0; i < threadCount; i++) {
            final int threadIndex = i;
            CompletableFuture<ThreadInfo> future = CompletableFuture.supplyAsync(() -> {
                // 使用新方法，在虚拟线程内部执行任务并获取线程信息
                return timeService.executeTaskAndGetThreadInfo(threadIndex);
            }, virtualThreadPool);
            
            futures.add(future);
        }
        
        // 阻塞等待所有线程执行完成，并转换为DTO
        List<ThreadInfo> threadInfos = futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        // 创建错误信息的ThreadInfo
                        return new ThreadInfo(-1, "Error", "Thread error: " + e.getMessage());
                    }
                })
                .collect(Collectors.toList());
        
        return ThreadInfoMapper.INSTANCE.toDtoList(threadInfos);
    }
}