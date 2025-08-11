package com.example.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池配置类
 * 定义和配置应用程序中使用的各种线程池
 * 
 * @author Claude
 * @since 1.0.0
 */
@Configuration
public class ThreadPoolConfig {
    
    /**
     * 虚拟线程池Bean
     * 使用Java 21的虚拟线程功能，适合I/O密集型任务
     * 使用自定义线程工厂为虚拟线程命名
     * 
     * @return 虚拟线程执行器服务
     */
    @Bean(name = "virtualThreadPool")
    public ExecutorService virtualThreadPool() {
        ThreadFactory virtualThreadFactory = Thread.ofVirtual()
                .name("virtual-worker-", 0)
                .factory();
        return Executors.newThreadPerTaskExecutor(virtualThreadFactory);
    }
    
    /**
     * 缓存线程池Bean
     * 根据需要创建新线程，适合短期异步任务
     * 
     * @return 缓存线程池执行器服务
     */
    @Bean(name = "cachedThreadPool")
    public ExecutorService cachedThreadPool() {
        return Executors.newCachedThreadPool();
    }
}