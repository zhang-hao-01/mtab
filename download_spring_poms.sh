#!/bin/bash
REPO_URL="https://repo1.maven.org/maven2"

POMS=(
"org/springframework/spring-core/6.1.6/spring-core-6.1.6.pom"
"org/springframework/spring-aop/6.1.6/spring-aop-6.1.6.pom"
"org/springframework/spring-beans/6.1.6/spring-beans-6.1.6.pom"
"org/springframework/spring-expression/6.1.6/spring-expression-6.1.6.pom"
"io/micrometer/micrometer-observation/1.12.5/micrometer-observation-1.12.5.pom"
"org/springframework/boot/spring-boot-autoconfigure/3.2.5/spring-boot-autoconfigure-3.2.5.pom"
"org/springframework/boot/spring-boot-starter-logging/3.2.5/spring-boot-starter-logging-3.2.5.pom"
"jakarta/annotation/jakarta.annotation-api/2.1.1/jakarta.annotation-api-2.1.1.pom"
"org/yaml/snakeyaml/2.2/snakeyaml-2.2.pom"
"org/springframework/boot/spring-boot-starter-json/3.2.5/spring-boot-starter-json-3.2.5.pom"
"org/springframework/boot/spring-boot-starter-tomcat/3.2.5/spring-boot-starter-tomcat-3.2.5.pom"
"io/lettuce/lettuce-core/6.3.2.RELEASE/lettuce-core-6.3.2.RELEASE.pom"
"org/springframework/data/spring-data-redis/3.2.5/spring-data-redis-3.2.5.pom"
"org/apache/tomcat/embed/tomcat-embed-el/10.1.20/tomcat-embed-el-10.1.20.pom"
"org/hibernate/validator/hibernate-validator/8.0.1.Final/hibernate-validator-8.0.1.Final.pom"
"org/springframework/spring-context-support/6.1.6/spring-context-support-6.1.6.pom"
"org/eclipse/angus/jakarta.mail/2.0.3/jakarta.mail-2.0.3.pom"
"org/springframework/boot/spring-boot-dependencies/3.2.0/spring-boot-dependencies-3.2.0.pom"
"com/mysql/mysql-connector-j/8.3.0/mysql-connector-j-8.3.0.pom"
"cn/hutool/hutool-parent/5.8.25/hutool-parent-5.8.25.pom"
"org/springframework/boot/spring-boot-starter-test/3.2.5/spring-boot-starter-test-3.2.5.pom"
)

for pom in "${POMS[@]}"; do
    dir=$(dirname "$pom")
    mkdir -p ~/.m2/repository/"$dir"
    echo "Downloading $pom..."
    curl -L -o ~/.m2/repository/"$pom" "$REPO_URL/$pom" 2>/dev/null && echo "OK" || echo "FAILED"
done

echo "All POM files downloaded"
