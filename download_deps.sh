#!/bin/bash

REPO_URL="https://repo1.maven.org/maven2"

# Spring Boot Starter Web
mkdir -p ~/.m2/repository/org/springframework/boot/spring-boot-starter-web/3.2.5
curl -L -o ~/.m2/repository/org/springframework/boot/spring-boot-starter-web/3.2.5/spring-boot-starter-web-3.2.5.pom "$REPO_URL/org/springframework/boot/spring-boot-starter-web/3.2.5/spring-boot-starter-web-3.2.5.pom"
curl -L -o ~/.m2/repository/org/springframework/boot/spring-boot-starter-web/3.2.5/spring-boot-starter-web-3.2.5.jar "$REPO_URL/org/springframework/boot/spring-boot-starter-web/3.2.5/spring-boot-starter-web-3.2.5.jar"

# Spring Boot Starter Data Redis
mkdir -p ~/.m2/repository/org/springframework/boot/spring-boot-starter-data-redis/3.2.5
curl -L -o ~/.m2/repository/org/springframework/boot/spring-boot-starter-data-redis/3.2.5/spring-boot-starter-data-redis-3.2.5.pom "$REPO_URL/org/springframework/boot/spring-boot-starter-data-redis/3.2.5/spring-boot-starter-data-redis-3.2.5.pom"
curl -L -o ~/.m2/repository/org/springframework/boot/spring-boot-starter-data-redis/3.2.5/spring-boot-starter-data-redis-3.2.5.jar "$REPO_URL/org/springframework/boot/spring-boot-starter-data-redis/3.2.5/spring-boot-starter-data-redis-3.2.5.jar"

# Spring Boot Starter Validation
mkdir -p ~/.m2/repository/org/springframework/boot/spring-boot-starter-validation/3.2.5
curl -L -o ~/.m2/repository/org/springframework/boot/spring-boot-starter-validation/3.2.5/spring-boot-starter-validation-3.2.5.pom "$REPO_URL/org/springframework/boot/spring-boot-starter-validation/3.2.5/spring-boot-starter-validation-3.2.5.pom"
curl -L -o ~/.m2/repository/org/springframework/boot/spring-boot-starter-validation/3.2.5/spring-boot-starter-validation-3.2.5.jar "$REPO_URL/org/springframework/boot/spring-boot-starter-validation/3.2.5/spring-boot-starter-validation-3.2.5.jar"

# Spring Boot Starter Mail
mkdir -p ~/.m2/repository/org/springframework/boot/spring-boot-starter-mail/3.2.5
curl -L -o ~/.m2/repository/org/springframework/boot/spring-boot-starter-mail/3.2.5/spring-boot-starter-mail-3.2.5.pom "$REPO_URL/org/springframework/boot/spring-boot-starter-mail/3.2.5/spring-boot-starter-mail-3.2.5.pom"
curl -L -o ~/.m2/repository/org/springframework/boot/spring-boot-starter-mail/3.2.5/spring-boot-starter-mail-3.2.5.jar "$REPO_URL/org/springframework/boot/spring-boot-starter-mail/3.2.5/spring-boot-starter-mail-3.2.5.jar"

# MyBatis Plus
mkdir -p ~/.m2/repository/com/baomidou/mybatis-plus-spring-boot3-starter/3.5.5
curl -L -o ~/.m2/repository/com/baomidou/mybatis-plus-spring-boot3-starter/3.5.5/mybatis-plus-spring-boot3-starter-3.5.5.pom "$REPO_URL/com/baomidou/mybatis-plus-spring-boot3-starter/3.5.5/mybatis-plus-spring-boot3-starter-3.5.5.pom"
curl -L -o ~/.m2/repository/com/baomidou/mybatis-plus-spring-boot3-starter/3.5.5/mybatis-plus-spring-boot3-starter-3.5.5.jar "$REPO_URL/com/baomidou/mybatis-plus-spring-boot3-starter/3.5.5/mybatis-plus-spring-boot3-starter-3.5.5.jar"

# MySQL Connector
mkdir -p ~/.m2/repository/com/mysql/mysql-connector-j/8.0.33
curl -L -o ~/.m2/repository/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.pom "$REPO_URL/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.pom"
curl -L -o ~/.m2/repository/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar "$REPO_URL/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar"

# Lombok
mkdir -p ~/.m2/repository/org/projectlombok/lombok/1.18.36
curl -L -o ~/.m2/repository/org/projectlombok/lombok/1.18.36/lombok-1.18.36.pom "$REPO_URL/org/projectlombok/lombok/1.18.36/lombok-1.18.36.pom"
curl -L -o ~/.m2/repository/org/projectlombok/lombok/1.18.36/lombok-1.18.36.jar "$REPO_URL/org/projectlombok/lombok/1.18.36/lombok-1.18.36.jar"

# Hutool
mkdir -p ~/.m2/repository/cn/hutool/hutool-all/5.8.25
curl -L -o ~/.m2/repository/cn/hutool/hutool-all/5.8.25/hutool-all-5.8.25.pom "$REPO_URL/cn/hutool/hutool-all/5.8.25/hutool-all-5.8.25.pom"
curl -L -o ~/.m2/repository/cn/hutool/hutool-all/5.8.25/hutool-all-5.8.25.jar "$REPO_URL/cn/hutool/hutool-all/5.8.25/hutool-all-5.8.25.jar"

# Jsoup
mkdir -p ~/.m2/repository/org/jsoup/jsoup/1.17.2
curl -L -o ~/.m2/repository/org/jsoup/jsoup/1.17.2/jsoup-1.17.2.pom "$REPO_URL/org/jsoup/jsoup/1.17.2/jsoup-1.17.2.pom"
curl -L -o ~/.m2/repository/org/jsoup/jsoup/1.17.2/jsoup-1.17.2.jar "$REPO_URL/org/jsoup/jsoup/1.17.2/jsoup-1.17.2.jar"

echo "Core dependencies downloaded"