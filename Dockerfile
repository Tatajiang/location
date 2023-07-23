# 通过 maven 进行打包
FROM maven:3.5.0-jdk-8-alpine AS builder

# 添加相关代码
ADD ./pom.xml pom.xml
ADD ./src src/

# 打包成 jar
RUN mvn clean package

# 设置 JDK 版本
FROM adoptopenjdk/openjdk8:jre8u382-b05-ubuntu

# 设置作者
LABEL maintainer="TataJiang9527@163.com"

VOLUME /tmp

ADD target/location-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9091

ENTRYPOINT ["java","-jar","app.jar"]

