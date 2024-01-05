# 前言

换了好多项目了，最初是多模块，但是通过一段时间实测发现，多模块项目实际意义很小，因为项目规模上去了，首先电脑就很卡。
后来，切成了独立的项目，发现我项目真的有人参考，但是这种分库造成了很大的下载的困扰。
所以现在又切回来了，用整合的方式开源。

# 简介

最初看了很多优秀博客，非常想自己写一套。后来新技术层出不穷，有pig、ruoyi等优秀开源项目，再后来看到了蘑菇博客给了我很大启发。我像蘑菇博客一样，围绕博客，自己去搭建一套基于微服务架构的后台管理系统.

尤其要说明的是，本项目只是把他们放到了一起，并不能算是多模块项目。在我看来，多模块意义属实不大，因为网关、认证等等，大部分服务，只需要开发一次，以后基本上不会有啥改动。多模块全部引入后，随着代码的增加，电脑会变得很卡，对性能要求增加，复杂度也增加了。因此，我认为多模块意义不是很大。

# 特点技术

项目整体上来说，还在开发中，代码风格还在一直调整。

* [x] `spring全家桶` springboot和cloud，包括注册中心和服务发现网关全都采用spring家族
* [x] `jpa` mybatis-plus的流行，让我再次对jpa充满了好奇
* [x] `oauth2` 我们公司的项目做了不少，账号统一了，但是技术非常不理想，所以我学习了oauth2这个东西
* [x] `vue3+ts+vite` 这是前端部分采用的主体技术，个人兴趣
* [x] `vite ssr和nuxt3` 主要实现了seo的部分
* [x] `tailwindcss和quasar` 已有的前端轮子或多或少都有一些不满意，所以就采用了一些新技术自己写

# 项目结构
 
* inyaa-admin 后台的主要业务代码
* inyaa-gateway 网关服务
* inyaa-oauth 授权服务器
* inyaa-picture 图片服务
* inyaa-resource 资源服务器
* inyaa-sakura 基于vite ssr的博客前端，这个目前seo不太理想
* inyaa-sakura-admin 基本vite+vue3+ts+quasar的后台管理页面部分
* inyaa-sakura-nuxt 基于nuxt的博客前端，这个google是可以搜的到的，还没进一步优化
* inyaa-vben 基于vben的后端管理前端项目
* inyaa-web 博客前端业务服务器，目前还没什么用

# inyaa-sakura博客前端展示

# 页面展示

![输入图片说明](https://github.com/a3318375/github-img/blob/main/QQ截图20220218160352.png)
![输入图片说明](https://github.com/a3318375/github-img/blob/main/QQ截图20220218160413.png)
![输入图片说明](https://github.com/a3318375/github-img/blob/main/QQ截图20220218160425.png)
![输入图片说明](https://github.com/a3318375/github-img/blob/main/QQ截图20220218160444.png)

# 后台页面展示

![输入图片说明](https://github.com/a3318375/github-img/blob/main/admin_01.png)
![输入图片说明](https://github.com/a3318375/github-img/blob/main/admin_02.png)

# 疑难解答

请直接评论或者提交issues，如果十分紧急，请加我QQ：184608371
