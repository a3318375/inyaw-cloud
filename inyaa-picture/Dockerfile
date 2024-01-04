FROM openjdk:17-jdk-alpine

#将本地项目jar包拷贝到Docker容器中的位置
ADD build/libs/inyaa-picture.jar ./
EXPOSE 8082
#开机启动
ENTRYPOINT ["java","-jar","/inyaa-picture.jar", "--spring.profiles.active=pro"]
