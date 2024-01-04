FROM openjdk:21-jdk-slim

#将本地项目jar包拷贝到Docker容器中的位置
ADD build/libs/inyaa-resource.jar ./
EXPOSE 8085
#开机启动
ENTRYPOINT ["java","-jar","/inyaa-resource.jar", "--spring.profiles.active=pro"]