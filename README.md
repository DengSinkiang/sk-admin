# skadmin权限管理系统


<div style="text-align: center">

[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/DengSinkiang/skadmin/blob/master/LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/DengSinkiang/skadmin.svg?style=social&label=Stars)](https://github.com/DengSinkiang/skadmin)
[![GitHub forks](https://img.shields.io/github/forks/DengSinkiang/skadmin.svg?style=social&label=Fork)](https://github.com/DengSinkiang/skadmin)

</div>

#### 项目简介

项目基于 Spring Boot 2.1.0 、 Spring boot Jpa、 Spring Security、Redis、Vue的前后端分离的权限管理系统，项目采用分模块开发方式， 权限控制采用 RBAC（Role-Based Access Control，基于角色的访问控制），支持数据字典、数据权限管理、前端菜单支持动态路由

#### 项目源码

|     |   后端源码  |   前端源码  |
|---  |--- | --- |
|  github   |  https://github.com/DengSinkiang/skadmin   |  https://github.com/DengSinkiang/skadmin-vue   |

##### 用户账号密码

- 管理员： admin
- 密码： 123456

#### 开发环境

- JDK：8
- IDE：IntelliJ IDEA
- 依赖管理：Maven
- 数据库：MySQL 5.7

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
    - 系统缓存 使用jedis将缓存操作可视化，并提供对redis的基本操作
    - 实时控制台 实时打印logback日志，更好的监控系统的运行状态
    - SQL监控 采用druid 监控数据库访问性能，默认用户名admin，密码123456
- 日志管理
    - 登录日志 使用aop记录用户登录日志
    - 操作日志 使用aop记录用户操作日志
    - 异常日志 记录操作过程中的异常，并且提供查看异常的堆栈信息
- 云存储管理
    - 七牛云存储
    - 腾讯云存储
    - 阿里云存储
- 消息管理
    - 短信服务 阿里大于
    - 邮件服务 配合富文本，发送html格式的邮件
- 系统工具
    - 定时任务 整合Quartz做定时任务，加入任务日志，任务运行情况一目了然
    - 代码生成 高灵活度一键生成前后端代码，减少百分之80左右的工作任务
    - 接口文档 使用的是 swagger-ui 
    - SM.MS免费图床 挺好用的一个图床，作为公共图片上传使用
    - 支付宝支付 
- 组件管理
    - 图标库 系统图标来自 https://www.iconfont.cn/
    - 富文本 集成wangEditor富文本
    - Markdown编辑器与Yaml编辑器
```
#### 项目结构
```
# 项目模块如下
- skadmin-common 公共模块
    - annotation 接口限流自定义注解
    - exception 项目统一异常的处理
    - mapper mapstruct的通用mapper
    - enums 常量枚举
    - redis redis缓存相关配置
    - swagger 接口文档配置
    - util 通用工具
- skadmin-admin 系统核心模块
    - skadmin-admin-interface
        - domain
	- dto
	- mapper
    - skadmin-admin-service
        - config 配置跨域与静态资源及JWT的安全过滤器配置
        - controller 控制器
	- service 业务实现
	    - spec 业务查询条件	    
- skadmin-log 系统日志模块
- skadmin-tools 系统第三方工具模块
- skadmin-generator 系统代码生成模块(还在修改中...)
- skadmin-quartz 定时任务模块
    - config 配置日志拦截器与WebSocket等
    - domain 实体类
    - repository 数据库操作
    - controller 控制器
    - service 业务实现
        - spec 业务查询条件
- skadmin-monitor 系统监控模块
```
#### 后端技术栈

- 基础框架：Spring Boot 2.1.0.RELEASE
- 持久层框架：Spring Boot Jpa
- 安全框架：Spring Security
- 缓存框架：Redis
- 日志打印：logback+log4jdbc
- 接口文档 Swagger2
- 其他：FastJson、AOP、MapStruct等

#### 前端技术栈
- node
- vue
- vue-router
- axios
- element ui
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
