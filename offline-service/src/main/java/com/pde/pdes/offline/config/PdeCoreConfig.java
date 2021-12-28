package com.pde.pdes.offline.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 * @author wzheng 属性读取配置类
 */
@Configuration
//@RefreshScope//用于自动刷新配置参数
public class PdeCoreConfig {
	
	// 设置核心线程数
		@Value("${pde.core.PdeDefaultThreadPool.CorePoolSize:30}")
		private int corePoolSize = 30;
		
		// 设置最大线程数
		@Value("${pde.core.PdeDefaultThreadPool.MaxPoolSize:150}")
		private int maxPoolSize = 150;
		
		// 设置队列容量
		@Value("${pde.core.PdeDefaultThreadPool.QueueCapacity:100}")
		private int queueCapacity = 100;
		
		// 设置线程活跃时间（秒）
		@Value("${pde.core.PdeDefaultThreadPool.KeepAliveSeconds:60}")
		private int keepAliveSeconds = 60;
		
		@Value("${pde.core.PdeDefaultThreadPool.ThreadNamePrefix:pde-thread-}")
		private String threadNamePrefix = "pde-thread-";
	

	@Bean(name="PdeDefaultThreadPool")
	public ThreadPoolTaskExecutor getExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(this.corePoolSize);
		taskExecutor.setMaxPoolSize(this.maxPoolSize);
		taskExecutor.setQueueCapacity(this.queueCapacity);
		taskExecutor.setKeepAliveSeconds(this.keepAliveSeconds);
		taskExecutor.setThreadNamePrefix(this.threadNamePrefix);
		// 设置拒绝策略
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

		// // 等待所有任务结束后再关闭线程池
		// executor.setWaitForTasksToCompleteOnShutdown(true);
		taskExecutor.initialize();
		return taskExecutor;
	}
}
