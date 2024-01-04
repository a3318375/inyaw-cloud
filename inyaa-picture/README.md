# inyaa-picture

inyaa-picture是inyaa系列的图片服务器，主要实现随即图查看，oss上传

# 使用框架

代码风格和规范还在调整，勿怪。

* [x] `spring boot`
* [x] `spring cloud`
* [x] `jpa`
* [x] `腾讯cos`
* [x] `consul-config`
* [x] `consul-discovery`
* [x] `lombok`

# 涉及后端

实际上，大部分api都在CRM后端API这里，博客后端API只有评论功能。

> 授权服务器: [https://github.com/a3318375/inyaa-oauth](https://github.com/a3318375/inyaa-oauth)<br/>
> 资源服务器: [https://github.com/a3318375/inyaa-resource](https://github.com/a3318375/inyaa-resource)<br/>
> CRM后端API: [https://github.com/a3318375/inyaa-admin](https://github.com/a3318375/inyaa-admin)<br/>
> spring网关: [https://github.com/a3318375/inyaa-gateway](https://github.com/a3318375/inyaa-gateway)<br/>
> 博客后端API: [https://github.com/a3318375/inyaa-web](https://github.com/a3318375/inyaa-web)<br/>
> 图片服务: [https://github.com/a3318375/inyaa-picture](https://github.com/a3318375/inyaa-picture)

# 二次开发

配置gradle_user_home,他影响你的jar包下载路径，避免C盘占用过高。

# 生产部署

1.可以通过drone
2.github action
3.手动打包

# 疑难解答

请直接评论或者提交issues，如果十分紧急，请加我QQ：184608371
