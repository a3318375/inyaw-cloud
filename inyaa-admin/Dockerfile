FROM openjdk:17-jdk-alpine
#将本地项目jar包拷贝到Docker容器中的位置
ADD build/libs/inyaa-admin.jar ./

RUN apk --update add tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone && \
    apk del tzdata && \
    rm -rf /var/cache/apk/*

EXPOSE 8080
EXPOSE 5080
#开机启动
ENTRYPOINT ["java","-jar","/inyaa-admin.jar", "--spring.profiles.active=pro"]
