# SocialConnect 社交平台

## 项目介绍
这是一个基于Spring Boot的社交媒体后端项目，提供了用户管理、帖子管理、评论管理、好友关系、关注关系等功能。

## 技术栈
- Spring Boot 2.7.14
  - Spring Web
  - Spring Security
  - Spring Validation
  - Spring Data Redis
  - Spring WebSocket
- MyBatis-Plus 3.5.3.1
- MySQL 8.0.33
- JWT 0.11.5
- Redis
- Hutool 5.8.20
- Lombok

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


## 开发计划

- [x] 项目基础架构搭建
- [x] 用户模块开发
- [x] 内容模块开发
- [x] 互动模块开发
- [ ] 通知模块开发
- [x] 搜索模块开发
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


## 其他可能的功能：
```
文件管理（除了头像，例如帖子中的图片/视频）
权限管理（更细粒度的用户角色和权限）
数据统计与分析
后台管理界面
```

## API文档

### 1. 用户相关接口
#### 1.1 用户注册
- 请求方式：POST
- 接口路径：`/api/user/register`
- 请求体：
```json
{
    "phone": "手机号",
    "password": "密码"
}
```

#### 1.2 用户登录
- 请求方式：POST
- 接口路径：`/api/user/login`
- 请求体：
```json
{
    "phone": "手机号",
    "password": "密码"
}
```

#### 1.3 获取用户信息
- 请求方式：GET
- 接口路径：`/api/user/{id}`

#### 1.4 更新用户信息
- 请求方式：PUT
- 接口路径：`/api/user/{update}`
- 请求体：
```json
{
    "nickname": "昵称",
    "avatar": "头像URL"
}
```

#### 1.5 检查手机号是否存在
- 请求方式：GET
- 接口路径：`/api/user/check-phone?phone=手机号`

#### 1.6 获取当前用户信息
- 请求方式：GET
- 接口路径：`/api/user/me`

### 2. 帖子相关接口
#### 2.1 发布帖子
- 请求方式：POST
- 接口路径：`/api/posts`
- 请求体：
```json
{
    "title": "标题",
    "content": "内容",
    "tags": "标签"
}
```

#### 2.2 获取所有帖子
- 请求方式：GET
- 接口路径：`/api/posts`

#### 2.3 获取单个帖子
- 请求方式：GET
- 接口路径：`/api/posts/{postId}`

#### 2.4 获取用户帖子列表
- 请求方式：GET
- 接口路径：`/api/posts/user/{userId}`

#### 2.5 更新帖子
- 请求方式：PUT
- 接口路径：`/api/posts/{postId}`
- 请求体：
```json
{
    "title": "标题",
    "content": "内容",
    "tags": "标签"
}
```

#### 2.6 删除帖子
- 请求方式：DELETE
- 接口路径：`/api/posts/{postId}`

#### 2.7 点赞帖子
- 请求方式：POST
- 接口路径：`/api/posts/{postId}/like`

#### 2.8 取消点赞
- 请求方式：DELETE
- 接口路径：`/api/posts/{postId}/like`

### 3. 评论相关接口
#### 3.1 发布评论
- 请求方式：POST
- 接口路径：`/api/posts/{postId}/comments`
- 请求体：
```json
{
    "content": "评论内容",
    "parentId": "父评论ID（可选）"
}
```

#### 3.2 获取帖子评论
- 请求方式：GET
- 接口路径：`/api/posts/{postId}/comments`

#### 3.3 获取评论回复
- 请求方式：GET
- 接口路径：`/api/posts/{postId}/comments/{commentId}/replies`

#### 3.4 删除评论
- 请求方式：DELETE
- 接口路径：`/api/posts/{postId}/comments/{commentId}`

### 4. 关注相关接口
#### 4.1 关注用户
- 请求方式：POST
- 接口路径：`/api/follows/{followerId}/{followingId}`

#### 4.2 取消关注
- 请求方式：DELETE
- 接口路径：`/api/follows/{followerId}/{followingId}`

#### 4.3 获取粉丝列表
- 请求方式：GET
- 接口路径：`/api/follows/followers/{userId}`

#### 4.4 获取关注列表
- 请求方式：GET
- 接口路径：`/api/follows/following/{userId}`

### 5. 好友相关接口
#### 5.1 发送好友请求
- 请求方式：POST
- 接口路径：`/api/friends/request/{userId}/{friendId}`

#### 5.2 接受好友请求
- 请求方式：PUT
- 接口路径：`/api/friends/accept/{requestId}`

#### 5.3 拒绝好友请求
- 请求方式：PUT
- 接口路径：`/api/friends/reject/{requestId}`

#### 5.4 删除好友
- 请求方式：DELETE
- 接口路径：`/api/friends/{userId}/{friendId}`

#### 5.5 获取好友请求列表
- 请求方式：GET
- 接口路径：`/api/friends/requests/{userId}`

#### 5.6 获取好友列表
- 请求方式：GET
- 接口路径：`/api/friends/{userId}`

### 6. 搜索相关接口
#### 6.1 搜索用户
- 请求方式：GET
- 接口路径：`/api/search/users?keyword=关键词`

#### 6.2 搜索帖子
- 请求方式：GET
- 接口路径：`/api/search/posts?keyword=关键词`

#### 6.3 搜索话题
- 请求方式：GET
- 接口路径：`/api/search/topics?keyword=关键词`

### 7. 文件相关接口
#### 7.1 上传头像
- 请求方式：POST
- 接口路径：`/api/files/avatar`
- 请求体：multipart/form-data
  - file: 图片文件

#### 7.2 删除头像
- 请求方式：DELETE
- 接口路径：`/api/files/avatar?filename=文件名`

## 注意事项
1. 所有需要认证的接口都需要在请求头中携带JWT token
2. 文件上传大小限制为10MB
3. 图片格式支持：jpg、jpeg、png、gif