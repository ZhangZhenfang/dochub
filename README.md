# dochub
## 1、项目功能

文件收集打包，excel文档在线填写系统。

功能1：可以进行文件的收集或者征集，例如用户可以发布一个公告，可以理解为新建一个文件夹，通过一个用户关系表，其他用户可以看到这个公告。接着其他就能往这个公告下面提交文件，即往这个文件夹添加文件，然后公告的发布者可以看到这个文件夹以及文件夹下的文件，然后可以将这个文件夹打包下载。

功能2：可以进行excel表格的在线填写，用户可以上传一个excel文件，通过用户关系表，其他用户可以看到这个excel文件，然后服务器可以解析excel表格生成一个table，用户可以在table下添加一条记录，目前这个功能只适用于那种只有表头的excel表格，一个用户也只能添加一行记录。上传excel的用户可以看到其他用户填写在这个表格里的内容，也可以下载这个填写后的excel文件。

[查看项目效果](http://www.the15373.com?_blank)

## 2、开发环境

jdk8、Spring、SpringMVC、Hibernate、MySql

## 3、项目结构说明

dochub

​	|——doc					相关文档

​	|——dochub-api 				接口

​	|——dochub-common		通用工具

​	|——dochub-service-excel 	excel表格上传和填写以及下载等服务实现

​	|——dochub-service-notice 	文件收集功能相关服务实现

​	|——dochub-service-user		用户相关功能服务实现

​	|——dochub-web 			web应用，服务消费者

​	|——pom.xml

## 4、启动流程

1. 启动zookeeper
2. 启动dochub-service-user
3. 启动dochub-service-excel和dochub-service-notice
4. 启动dochub-web

## 5、Contact me 

- QQ:1228436617
- Tel:17839911994

