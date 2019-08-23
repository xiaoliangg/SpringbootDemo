package com.cdtelecom;


import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

//@RestController // 启动类无需此注解
//++++++++++++++++++++++++++++++++++++++++++++++++
//使用 @EnableAutoConfiguration、@ComponentScan 或 @SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan
//++++++++++++++++++++++++++++++++++++++++++++++++
@SpringBootApplication
//++++++++++++++++++++++++++++++++++++++++++++++++
//@ComponentScan(basePackages = {"com.lanhuigu","com.ghg"})// string[]   可以搭配 SpringBootApplication 使用，指定扫描包
@EnableScheduling //告诉Spring创建一个task executor，如果我们没有这个标注，所有@Scheduled标注都不会执行
@MapperScan("com.cdtelecom.mapper")
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


//	@RequestMapping("/hello")
//    String home() {
//		return "Hello World2!";
//	}
//
//	@RequestMapping("/testGet")
//    String getCountryByIp(@RequestParam("ip") String ip) {
//		System.out.println(ip);
//        return "111";
//	}
}
