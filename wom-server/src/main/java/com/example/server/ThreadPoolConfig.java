package com.example.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class ThreadPoolConfig {
    @Bean("pPool")
    public Executor piping() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 设置线程名称前缀
        threadPoolTaskExecutor.setThreadNamePrefix("pipingChannel-");
        // 设置核心线程数 队列未满时的最大实际线程数
        threadPoolTaskExecutor.setCorePoolSize(4);
        // 设置最大线程数 队列满时增加实际线程数直至最大
        threadPoolTaskExecutor.setMaxPoolSize(4);
        // 设置工作队列大小
        threadPoolTaskExecutor.setQueueCapacity(100);
        // 设置拒绝策略 队列满且实际线程数最大时的拒绝策略
//        AbortPolicy策略：默认策略，如果线程池队列满了丢掉这个任务并且抛出RejectedExecutionException异常。
//        DiscardPolicy策略：如果线程池队列满了，会直接丢掉这个任务并且不会有任何异常。
//        DiscardOldestPolicy策略：如果队列满了，会将最早进入队列的任务删掉腾出空间，再尝试加入队列。
//        CallerRunsPolicy策略：如果添加到线程池失败，那么主线程会自己去执行该任务，不会等待线程池中的线程去执行。
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 初始化线程池
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
