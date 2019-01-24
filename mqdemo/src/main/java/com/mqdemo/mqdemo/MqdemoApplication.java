package com.mqdemo.mqdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
public class MqdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqdemoApplication.class, args);
    }


    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int core = Runtime.getRuntime().availableProcessors();
        // 设置核心线程数
        executor.setCorePoolSize(core);
        // 设置最大线程数
        executor.setMaxPoolSize(core * 2 + 1);
        // 设置队列容量
        executor.setQueueCapacity(50);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("springboot-executor");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
