项目介绍


基于springcloud和vue微服务，前后端分离的后台管理框架，可以作为springcloud和vue的入门学习框架，亦可以作为一个基础脚手架进行二次开发

模块介绍

1.	grid-common：公共模块，主要提供权限模型、本系统通用工具。供后续增加新的微服务时引用。
2.	grid-nacos-1.1.3：spring cloud注册中心模块。请自行下载nacos1.1.3版本。
3.	grid-gateway：spring cloud 网关模块。
4.	grid-srvadmin ：spring boot admin
5.	grid-privadmin：应用基础管理模块，提供菜单管理、角色管理、用户管理、区域管理（PS系统内用了dept代表，应该用region更合适）及相关授权管理等。
6.	grid-cms ：内容管理服务

使用说明


1.新建数据库，导数数据库脚本.在gridapp-Doc下面

2.启动nacos 注册中心
导入nocaos配置，脚本在gridapp-Doc下面。  目前各个服务的bootstrap.yml配置文件执行localhost:8848

前端vue登录用户名和密码
用户名和密码为：
admin
111111




