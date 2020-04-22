###  **spring-cloud-plus** 
### 项目地址
- https://gitee.com/gourd-hu/spring-cloud-plus
- https://github.com/hxnlyw/spring-cloud-plus
### 系统介绍  
spring-cloud-plus 是以spring-cloud-alibaba为基础并整合一些常用框架的分布式基础开发平台。
项目以组件模块的方式构建，实现项目模块可插拔组装。工作中遇到的一些常用框架，我都会整合到此项目，并持续更新。
### 目标
- 成为一个简单易用、快速高效、功能丰富、安全稳定的分布式项目脚手架。
- 让每个人都可以独立、快速、高效地开发一套分布式项目！

### 技术栈
- 基础：springboot + springcloud
- 持久化：MybatisPlus
- 连接池：Druid
- 数据库：Mysql
- 项目构建：Maven
- API网关： Gateway
- 日志：Logback
- 注册、配置中心： Nacos
- 服务调用： Feign + Ribbon
- 熔断、降级：Sentinel
- 分布式事务：Seata
- 分布式锁：Redisson
- 鉴权： Shiro + jwt-token
- 缓存： Redis、springCache
- 工作流：Activiti
- 定时任务： Quartz
- 消息中间件：ActiveMq
- 文件操作：FastDFS、EasyExcel
- 搜索存储引擎：Elasticsearch
- 接口文档：Swagger、knife4j
- 应用监控：spring-boot-admin
- 前置负载：Nginx
- 链路追踪：Skywalking
- 项目部署：docker-compose

### 组件模块
|  模块   |   描述  |   功能  |
| --- | --- | --- |
|  cmpt-base   | 基础模块    |   项目所必须的依赖包和配置和基础类  |
|  cmpt-core   | 核心模块    |   项目公共的工具类等  |
|  cmpt-cloud  | 分布式模块  |   注册、配置中心、分布式事务等    |
|  cmpt-rbac   | 鉴权模块    |   RBAC鉴权模型，用户、角色、权限分配等    |
|  cmpt-cache  | 缓存模块    |  redis集群、redisson分布式锁、springcache注解式缓存  |
|  cmpt-quartz   | 定时调度模块  |  quartz动态定时任务，并持久化到mysql数据库  |
|  cmpt-activiti | 工作流模块    |  activiti整合，及请假Demo   |
|  cmpt-notice   | 通讯模块    |  邮件、短信、websocket等  |
|  cmpt-file     | 文件模块    | 文件上传、下载、预览，Excel导入导出等     |
|  cmpt-es       | 搜索引擎模块  |   elasticSearch 搜索引擎整合 |
|  cmpt-activemq   |  消息模块   |  消息的发送和消费   |
|  cmpt-log    | 日志模块    |  logback日志、操作日志记录等   |
|  cmpt-flyway|  脚本执行模块   |  数据库脚本自动执行   |
|  generator-boot   |  代码生成模块   |  mybatisPlus的代码生成工具，一键生成基础代码   |

### 项目模块  
|  模块   |  描述   |   功能  |
| --- | --- | --- |
| boot-admin-web    |  应用监控模块  |  监控应用线程、内存、垃圾回收、配置等监控  |
| gateway-web    |  网关模块  |  负责服务路由转发，可作为服务统一入口  |
| openapi-web    |  开放平台模块  |  外部接口调用入口、签名鉴权等  |
| admin-web    |  主项目  |  整合了所有的组件模块,启动这个模块，就可以测试所有的功能啦  |
| sub-webb   |  辅助项目|   简单的cloud项目，用于测试分布式服务调用和分布式事务等 |
| sub-api   |  辅助API模块|   封装feign调用，方便复用 |

## 技术架构图
![技术架构图](https://images.gitee.com/uploads/images/2020/0414/133607_eec8b0c6_4866886.jpeg "架构.jpg")

### 文档
线上接口文档：http://47.103.5.190:10001/doc.html  
![接口文档](https://images.gitee.com/uploads/images/2020/0410/142244_fefca957_4866886.jpeg "1586499715(1).jpg")

### 如何开始
项目启动需要设置 local环境配置：
![启动](https://images.gitee.com/uploads/images/2020/0410/134332_1b8d28fd_4866886.jpeg "1586497382(1).jpg")
所有项目模块都可以直接启动，配置信息都放在Nacos中了。如果需要修改配置地址信息等，可自己添加application.yaml然后修改，项目中提供了一份demo文件可参考：
![配置](https://images.gitee.com/uploads/images/2020/0410/134808_6d0180a8_4866886.jpeg "1586497644(1).jpg")
有时会报Sentinel的配置类型转换异常，需要调整JDK版本，我使用的版本是：1.8.0_231 是可以正常启动的。具体参看：
![sentinel异常](https://images.gitee.com/uploads/images/2020/0410/135701_27b74345_4866886.png "227dcfc3ddcce4aeaed6055423ee8a8.png")

### 部署
- 项目中设计的一些技术服务端都是使用docker部署的，包括：mysql、redis、nacos、seata等。
大家可参考我的博客文章：https://blog.csdn.net/HXNLYW/article/details/88950291     
- 项目也是用docker-compose部署的，具体可参考：
jar包部署：https://blog.csdn.net/HXNLYW/article/details/104613163  
war包部署：https://blog.csdn.net/HXNLYW/article/details/105585745  
- Skywalking集成： https://blog.csdn.net/HXNLYW/article/details/105574329
### 集成功能  
**核心功能:**  
- 用户权限管理:   
>   框架：shiro +jwt，使用jwt-token作为用户身份Id；  
>   支持承租人模式；token续期等  
- 网关: 
> 负责服务路由转发，可作为服务统一入口
- 开放平台:
> 通过签名验证方式，鉴权外部接口调用    
- 动态数据源：  
>   动态多数据源（支持读写分离，如果只有一个数据库，可将master和slave配置成一样即可）;    
>   数据库连接密码加密;    
- 缓存：  
>   框架：[springCache](https://www.jianshu.com/p/6db623355e11)、redis、redisson ;   
>   封装redis、redisson工具类;                          
- 分布式事务：
>   框架：seata（AT模式）; 集成以nacos为配置中心   
- 定时任务： 
>   框架：quartz；  
>   quartz表结构自动生成;  
>   业务代码动态增加定时任务;   
>   数据持久化到Mysql数据库;   
- 分布式调用：  
>   注册发现中心：Nacos;   
>   feignClient远程调用;  
>   ribbon负载均衡、sentinel熔断降级 ;        
- 配置中心：
>   框架：nacos; 支持配置及日志等级热更新；           
- 限流熔断:  
>   框架：sentinel;接口限流、降级、系统保护、热点数据限流、权限设置等;  
>   规则持久化到Nacos;  
- 通讯：  
>   aliyun短信通知; 
>   邮件通知(文本、html、附件、模板);
- 文件：
>   openoffice在线预览文件; 
>   easyexcel实现Excel文件导入导出;
>   fastDfs文件上传、下载; 
>   FlyingSaucer+thymeleaf实现动态模板文件转pdf打印; 
- 工作流
>   框架：activiti  
>   请假流程Demo       
- 消息：
>   框架：activeMq
>   支持点对点和订阅发布模式，消息持久化
- async：  
>   支持@Async注解异步方法；  
>   异步线程池直接使用。
- 日志记录：  
>   logback日志记录，以小时为单位生成日志文件；  
>   操作日志记录，根据配置策略规定所有接口都生成日志还是指定注解的接口生成日志；
>   记录请求地址、参数、返回、异常及接口耗时等并持久化到数据库默认保留30天的日志。
- 搜索引擎 
>   框架：elasticSearch; 分布式、RESTful 风格的搜索和数据分析引擎；     
- 工具类:    
>    1)地址ip获取工具(IpAddressUtil);  
>    2)日期处理工具(DateUtil);  
>    3)分布式全局唯一订单号生成、分布式全局唯一主键id生成工具(OrderNumberUtil、IdGeneratorUtil);  
>    4)md5加密工具、AES加解密工具(Md5Util、AesHopeUtil);  
>    5)汉字拼音生成工具(Pinyin4jUtil);   
>    6)铭感词脱敏工具(SensitiveInfoUtil); 
>    7)集合转换复制工具(CollectionUtil);  
>    8)redis操作工具类、redisson锁工具类(RedisUtil、RedisLockUtil);  
>    9)RestTemplate远程调用工具了(RestTemplateUtil);  
> 
- 其他功能:  
>  1)统一异常处理，自定义业务异常；    
>  2)spring-retry接口重试,自定义接口重试次数、间隔、和重试的异常;     
>  3)AOP接口防重复提交，唯一条件：指定时间内，用户id+请求url+参数 控制唯一性;    
>  4)实体类公共字段填充：is_deleted,version,created_by,created_time,updated_by,updated_time  ；  
>  5)p6spy sql日志打印，打印sql耗时、替换参数后的sql;  
>  6)jasypt文本加解密，可用于数据库密码、redis密码、邮箱密码等加密；      
>    密文生成：https://blog.csdn.net/HXNLYW/article/details/98635913  
>  7)图形验证码生成校验
          
**辅助功能：**  
- knife4j:   
>  接口文档、接口调试；  
>  本地地址：http://localhost:10001/doc.html
- flyway：  
>  数据库脚本自动执行，只需要将sql脚本放在 /db目录下，项目启动就会自动执行脚本。  
- generator：  
>  mybatisPlus自动生成基础代码  
- druid监控：  
>  本地地址：http://localhost:10001/druid/login.html 账号密码：admin  
- springbootadmin监控：  
>  springboot应用监控，线程、内存、垃圾回收、配置等监控；  
>  本地地址：http://localhost:8769  
  
### 作者
名字：葫芦胡  
邮箱：13584278267@163.com  
博客：https://blog.csdn.net/HXNLYW  
微信号：13584278267 (欢迎咨询)
