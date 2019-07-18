# Skadmin management system


<div style="text-align: center">

[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/DengSinkiang/skadmin/blob/master/LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/DengSinkiang/skadmin.svg?style=social&label=Stars)](https://github.com/DengSinkiang/skadmin)
[![GitHub forks](https://img.shields.io/github/forks/DengSinkiang/skadmin.svg?style=social&label=Fork)](https://github.com/DengSinkiang/skadmin)

</div>

#### Learning exchange group

QQ Group：149952596

#### Pay attention

Add the RabbitMQ and ElasticSearch demos to the dev branch. If you need to practice, please install Baidu yourself and then practice.

#### The source code of the project

|     |   the source code of backend  |   the source code of front-end  |
|---  |--- | --- |
|  github   |  https://github.com/DengSinkiang/skadmin   |  https://github.com/DengSinkiang/skadmin-vue   |

##### User account password
```
- Administrator： admin
- Password： 123456
```
#### The development environment
```
- JDK：8
- IDE：IntelliJ IDEA
- The Dependency management：Maven
- Database：MySQL 5.7
```
#### Running the project
```
- This project uses redis. If it is not installed, please install it by Baidu.
- Run the SkAdmin.java directly to start the background service.
```
#### The functional module
```
- System Management
    - User Management # Provide user related configuration
    - Role Management # Assign permissions and menus
    - Authority Management # Permission refinement to interface
    - Menu Management # Menu dynamic routing has been implemented, the backend is configurable, and multi-level menus are supported.
    - Dept Management
    - Job Management
    - Dictionary Management
- System monitoring
    - System cache # Visualize caching operations with jedis and provide basic operations on redis
    - Real-time console # Print logback logs in real time to better monitor the operating status of the system
    - SQL monitoring # Monitor database access performance with druid, default username admin, password 123456
- Log manageMent
    - Login log # Use aop to log user login logs
    - Operation log # Use aop to record user action logs
    - Exception log # Record exceptions during operation and provide stack information for viewing exceptions
- Cloud storage management
    - QiNiu cloud storage file upload, download
- Message management
    - Short message service # Ali dayu
    - Email service # Send text in html format with rich text
- System Tool
    - Timed task # Integrate Quartz to do scheduled tasks, join the task log, and see the task running at a glance
    - Code generation # High flexibility, one-click generation of front and rear code, reducing work tasks by 80% or so
    - Api document # Using swagger-ui
    - SM.MS free map bed # A very easy to use picture bed, used as a public image upload
```
#### Project structure
```
# Project module is as follows
- skadmin-common # Common module
    - annotation # Interface current limit custom annotation
    - exception # Uniform exception handling
    - mapper # Generic mapper for mapstruct
    - enums # Constant enum
    - redis # Redis cache related configuration
    - response # Unified encapsulation return information
    - swagger # Api document configuration
    - util # General tool
- skadmin-admin # System core module
    - skadmin-admin-interface
        - domain
	- dto
	- mapper
	- query # Query related conditions
    - skadmin-admin-service
        - config # Configure cross-domain and static resources and JWT security filter configuration
        - controller 
	- repository # Database operation
	- service # Business realization
	SkAdmin.java # Startup class	    
- skadmin-log # System log module
    - skadmin-log-interface
        - annotation
        - domain
        - dto
        - mapper
    - skadmin-log-service
        - aspect
        - controller
        - respository
        - service
- skadmin-tool # System third party tool module
- skadmin-generator # System code generation module
- skadmin-monitor # System monitoring module
    - config # Configure log interceptors and WebSockets
    - domain # Entity class
    - repository # Database operation
    - controller 
    - service # Business realization
- skadmin-quartz # Timed task module
```
#### Backend technology stack
```
- Basic framework: Spring Boot 2.1.0.RELEASE
- Dao framework: Spring Data JPA
- Security framework: Spring Security
- Cache framework: Redis
- Log printing: logback+log4jdbc
- Api document: Swagger2
- Other: FastJson、AOP、MapStruct等
```
#### Front-end technology stack
```
- node
- vue
- vue-router
- axios
- element ui
```
#### System preview

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
