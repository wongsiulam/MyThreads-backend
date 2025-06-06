# SocialConnect 社交平台

一个基于Spring Boot的综合性社交平台，提供类似微博和朋友圈的功能。

## 技术栈

- 核心框架：Spring Boot 2.7.x
- 安全框架：Spring Security + JWT
- 持久层框架：MyBatis-Plus
- 数据库：MySQL 8.0
- 缓存：Redis
- 消息队列：RabbitMQ
- 搜索引擎：Elasticsearch
- 文件存储：阿里云OSS/MinIO
- 实时通讯：WebSocket + Netty

## 功能特性

- 用户管理：注册、登录、个人信息管理
- 社交关系：关注、粉丝系统
- 内容管理：动态发布、浏览、分类
- 互动功能：点赞、评论、转发
- 即时通讯：私信系统
- 通知系统：系统通知、互动通知
- 数据分析：用户行为分析、内容推荐

## 快速开始

### 环境要求

- JDK 11+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### 本地开发

1. 克隆项目
```bash
git clone https://github.com/yourusername/social-connect.git
```

2. 创建数据库
```sql
CREATE DATABASE social_connect DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. 修改配置
编辑 `src/main/resources/application.yml` 文件，配置数据库连接等信息。

4. 运行项目
```bash
mvn spring-boot:run
```

## 项目结构

```
src/main/java/com/socialconnect
├── config          // 配置类
├── controller      // 控制器
├── service         // 服务层
├── repository      // 数据访问层
├── entity          // 实体类
├── dto             // 数据传输对象
├── vo              // 视图对象
├── common          // 公共组件
│   ├── constant    // 常量
│   ├── exception   // 异常
│   ├── util        // 工具类
│   └── response    // 响应封装
└── security        // 安全相关
```

## 开发计划

- [x] 项目基础架构搭建
- [ ] 用户模块开发
- [ ] 内容模块开发
- [ ] 互动模块开发
- [ ] 通知模块开发
- [ ] 搜索模块开发
- [ ] 性能优化
- [ ] 部署文档

## Java常见注解举例与作用

```
注解	作用简述
@SpringBootApplication	声明主启动类，自动配置Spring Boot项目
@RestController	声明一个控制器，返回JSON数据
@GetMapping("/xxx")	声明一个GET接口，路径为/xxx
@Autowired	自动注入依赖对象
@Service	声明服务层组件
@Repository	声明数据访问层组件
@Entity	声明一个JPA实体类（数据库表映射）
@Configuration	声明配置类
@Bean	声明一个Bean交给Spring容器管理
```