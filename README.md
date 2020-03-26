# SpringbootDemo
Springboot demo by xiaoliang。



架构技术:
-------------------	
- jdk1.8+springboot2.1.6+Tomcat9.0.21+spring5.1.8+springmvc5.1.8+mybatis3.5.0
- log:slf4j-1.7.26+logback-1.2.3
- other:gson  lombok 


基本功能支持: 
-------------------	
- 1、打印log
- 2、资源属性注入，String、map
- 3、mysql数据库连接，及crud
- 4、定时任务
- 5、消息分发，资源属性map注入
- 6、事务
- 7、log打印sql语句
- 8、请求报文校验   spring封装的Hibernate校验框架
- 9、异常处理     @ControllerAdvice + @ExceptionHandler 全局处理 Controller 层异常
- 10、lombok新增
- 11、springboot 多profile以支持多种线上环境;junit 单元测试 支持多种Profile
- 12、捕获全局异常，并邮件预警


打包:
-------------------	
From the command line with Maven installed:
- cd CdTelecom
- mvn clean install

运行:
-------------------
###### dev 
java -jar CdTelecom-app-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
###### uat 
java -jar CdTelecom-app-0.0.1-SNAPSHOT.jar --spring.profiles.active=uat