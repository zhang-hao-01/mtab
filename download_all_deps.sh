#!/bin/bash
REPO_URL="https://repo1.maven.org/maven2"

BOMS=(
"org/assertj/assertj-bom/3.24.2/assertj-bom-3.24.2.pom"
"org/springframework/spring-framework-bom/6.1.6/spring-framework-bom-6.1.6.pom"
"org/springframework/data/spring-data-bom/2023.1.5/spring-data-bom-2023.1.5.pom"
"com/fasterxml/jackson/jackson-bom/2.15.4/jackson-bom-2.15.4.pom"
"io/netty/netty-bom/4.1.109.Final/netty-bom-4.1.109.Final.pom"
"io/micrometer/micrometer-bom/1.12.5/micrometer-bom-1.12.5.pom"
"org/springframework/boot/spring-boot-starter/3.2.5/spring-boot-starter-3.2.5.pom"
"org/springframework/boot/spring-boot/3.2.5/spring-boot-3.2.5.pom"
"org/springframework/spring-context/6.1.6/spring-context-6.1.6.pom"
"org/springframework/spring-web/6.1.6/spring-web-6.1.6.pom"
"org/springframework/spring-webmvc/6.1.6/spring-webmvc-6.1.6.pom"
)

for bom in "${BOMS[@]}"; do
    dir=$(dirname "$bom")
    mkdir -p ~/.m2/repository/"$dir"
    curl -L -o ~/.m2/repository/"$bom" "$REPO_URL/$bom"
done

echo "BOM files downloaded"
