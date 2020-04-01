# 云峰手术平台

核心架构：springBoot + mybatisPlus + jwt + swagger 

主要依赖于工具：jetCache、  hutool、 lombok、 weixin-java-mp

**项目结构** 
```
bee-cloud-java

├─common 公共模块
│  ├─annotation
│  ├─aspect
│  ├─constant
│  ├─entity
│  ├─enums
│  ├─exception
│  ├─handler
│  ├─interceptor
│  ├─threadpool
│  ├─util
│  └─validator
│ 
├─config 配置信息
│ 
├─modules 功能模块
│  ├─admin  后台管理模块
│  |  ├─dss 日间协会管理平台
│  |  ├─followup 云随访管理端后台
│  |  ├─from 表单
│  |  ├─login 登录
│  |  └─upms  权限管理
│  ├─app    app接口
│  ├─common 公共api
│  ├─home   日间协会官网
│  └─mp     微信
│ 
├─Application 项目启动类
│  
├──resources 
│  ├─mapper SQL对应的XML文件
│  ├─static 静态资源
│  └─template 代码模板

```
