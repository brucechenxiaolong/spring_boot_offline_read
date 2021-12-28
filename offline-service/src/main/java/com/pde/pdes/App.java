package com.pde.pdes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication

//@EnableDiscoveryClient //服务注册客户端
//@EnableCircuitBreaker //断路器
//@EnableFeignClients //表示为Fegin客户端

@EnableSwagger2  //添加Swagger2应用，通过http://ip:port/swagger-ui.html进行访问
@EnableScheduling  //添加任务调度支持
@EnableAsync //添加异步处理支持
public class App {
	
    public static void main( String[] args ){
    	System.out.println("路径："+System.getProperty("user.dir"));
    	SpringApplication.run(App.class, args);
    }

}
