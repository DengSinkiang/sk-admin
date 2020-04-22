# sk-admin 管理系统


<div style="text-align: center">

[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/DengSinkiang/skadmin/blob/master/LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/DengSinkiang/skadmin.svg?style=social&label=Stars)](https://github.com/DengSinkiang/skadmin)
[![GitHub forks](https://img.shields.io/github/forks/DengSinkiang/skadmin.svg?style=social&label=Fork)](https://github.com/DengSinkiang/skadmin)

</div>

#### 学习交流

QQ 群：149952596

#### 注意

dev 分支新增 RabbitMQ、ElasticSearch 的 demo，如需练习请自行百度安装，然后练习

#### 在线文档

https://www.kancloud.cn/sinkiang/skadmin_document/content

#### 项目源码

|     |   后端源码  |   前端源码  |
|---  |--- | --- |
|  github   |  https://github.com/DengSinkiang/sk-admin   |  https://github.com/DengSinkiang/sk-admin-web   |

##### 用户账号密码
```
- 管理员： admin
- 密码： 123456
```
#### 开发环境
```
- JDK：8
- IDE：IntelliJ IDEA
- 依赖管理：Maven
- 数据库：MySQL 5.7
```
#### 运行项目
```
- 本项目用到 redis，没安装请自行百度安装
- 直接运行 SkAdmin.java 即可启动后台服务
```
#### 功能模块
```
- 系统管理
    - 用户管理 提供用户的相关配置
    - 角色管理 对权限与菜单进行分配
    - 权限管理 权限细化到接口
    - 菜单管理 已实现菜单动态路由，后端可配置化，支持多级菜单
    - 部门管理
    - 岗位管理
    - 字典管理 
- 系统监控
    - 系统缓存 使用 jedis 将缓存操作可视化，并提供对 redis 的基本操作
    - 实时控制台 实时打印 logback 日志，更好的监控系统的运行状态
    - SQL 监控 采用 druid 监控数据库访问性能，默认用户名 admin，密码 123456
- 日志管理
    - 登录日志 使用 aop 记录用户登录日志
    - 操作日志 使用 aop 记录用户操作日志
    - 异常日志 记录操作过程中的异常，并且提供查看异常的堆栈信息
- 云存储管理
    - 七牛云存储 文件上传、下载
- 消息管理
    - 短信服务 阿里大于
    - 邮件服务 配合富文本，发送 html 格式的邮件
- 系统工具
    - 定时任务 整合 Quartz 做定时任务，加入任务日志，任务运行情况一目了然
    - 代码生成 高灵活度一键生成前后端代码，减少百分之 80 左右的工作任务
    - 接口文档 使用的是 swagger-ui 
    - SM.MS 免费图床 挺好用的一个图床，作为公共图片上传使用
```
#### 项目结构
```
# 项目模块如下
- skadmin-common 公共模块
    - annotation 接口限流自定义注解
    - exception 项目统一异常的处理
    - mapper mapstruct 的通用 mapper
    - enums 常量枚举
    - redis redis 缓存相关配置
    - response 统一封装返回信息
    - swagger 接口文档配置
    - util 通用工具
- skadmin-admin 系统核心模块
    - skadmin-admin-interface
        - entity
	    - dto
	- mapper
	- query 查询相关的条件
    - skadmin-admin-service
        - config 配置跨域与静态资源及 JWT 的安全过滤器配置
        - controller 控制器
	- repository 数据库操作
	- service 业务实现
	SkAdmin.java 启动类	    
- skadmin-log 系统日志模块
    - skadmin-log-interface
        - annotation
        - entity
            - dto
        - mapper
    - skadmin-log-service
        - aspect
        - controller
        - respository
        - service
- skadmin-tool 系统第三方工具模块
- skadmin-generator 系统代码生成模块
- skadmin-monitor 系统监控模块
    - config 配置日志拦截器与 WebSocket 等
    - entity 实体类
    - repository 数据库操作
    - controller 控制器
    - service 业务实现
- skadmin-quartz 定时任务模块
```
#### 后端技术栈
```
- 基础框架：Spring Boot 2.1.0.RELEASE
- 持久层框架：Spring Data JPA
- 安全框架：Spring Security
- 缓存框架：Redis
- 日志打印：logback+log4jdbc
- 接口文档 Swagger2
- 其他：FastJson、AOP、MapStruct 等
```
#### 前端技术栈
```
- node
- vue
- vue-router
- axios
- element ui
```
#### 系统预览

<table>
    <tr>
        <td><img src="https://mmmlf.tmuyun.com/67E4C17C279F5AA5441C06B4EDF19EA7.jpg"/></td>
        <td><img src="https://mmmlf.tmuyun.com/5D3CD4CE037BF05BBEBF4E7343BD2B2A.jpg"/></td>
    </tr>
    <tr>
        <td><img src="https://mmmlf.tmuyun.com/221883A1DB6C90DEC7D897BE19E1655A.jpg"/></td>
        <td><img src="https://mmmlf.tmuyun.com/52D529E8E62C5952F902F09CF708B131.jpg"/></td>
    </tr>
    <tr>
        <td><img src="https://mmmlf.tmuyun.com/26033FD6718D1857DF0D6DDE16B88285.jpg"/></td>
        <td><img src="https://mmmlf.tmuyun.com/BF58423BD9B538D18E8ED2151818E09D.jpg"/></td>
    </tr>
    <tr>
        <td><img src="https://mmmlf.tmuyun.com/01DD8D0FFD9F68AF95ACC7CF740D8A61.jpg"/></td>
        <td><img src="https://mmmlf.tmuyun.com/FB60E74D1E77F6A8A859DCCDA2945E30.jpg"/></td>
    </tr>
</table>
