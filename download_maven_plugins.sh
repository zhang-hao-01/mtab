#!/bin/bash
REPO_URL="https://repo1.maven.org/maven2"

PLUGINS=(
"org/apache/maven/plugins/maven-resources-plugin/3.3.1/maven-resources-plugin-3.3.1.pom"
"org/apache/maven/plugins/maven-jar-plugin/3.3.0/maven-jar-plugin-3.3.0.pom"
"org/apache/maven/plugins/maven-surefire-plugin/3.1.2/maven-surefire-plugin-3.1.2.pom"
"org/apache/maven/plugins/maven-install-plugin/3.1.1/maven-install-plugin-3.1.1.pom"
"org/apache/maven/plugins/maven-deploy-plugin/3.1.1/maven-deploy-plugin-3.1.1.pom"
"org/apache/maven/plugins/maven-clean-plugin/3.3.2/maven-clean-plugin-3.3.2.pom"
"org/apache/maven/plugins/maven-site-plugin/3.12.1/maven-site-plugin-3.12.1.pom"
)

for plugin in "${PLUGINS[@]}"; do
    dir=$(dirname "$plugin")
    mkdir -p ~/.m2/repository/"$dir"
    echo "Downloading $plugin..."
    curl -L -o ~/.m2/repository/"$plugin" "$REPO_URL/$plugin" 2>/dev/null && echo "OK" || echo "FAILED"
done

echo "Maven plugins downloaded"
