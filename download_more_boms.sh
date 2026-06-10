#!/bin/bash
REPO_URL="https://repo1.maven.org/maven2"

BOMS=(
"io/zipkin/reporter2/zipkin-reporter-bom/2.16.3/zipkin-reporter-bom-2.16.3.pom"
"io/dropwizard/metrics/metrics-parent/4.2.25/metrics-parent-4.2.25.pom"
"org/eclipse/ee4j/project/1.0.9/project-1.0.9.pom"
"org/infinispan/infinispan-build-configuration-parent/14.0.27.Final/infinispan-build-configuration-parent-14.0.27.Final.pom"
"com/fasterxml/oss-parent/50/oss-parent-50.pom"
"org/eclipse/ee4j/project/1.0.8/project-1.0.8.pom"
"org/apache/logging/logging-parent/10.1.1/logging-parent-10.1.1.pom"
"io/prometheus/parent/0.16.0/parent-0.16.0.pom"
)

for bom in "${BOMS[@]}"; do
    dir=$(dirname "$bom")
    mkdir -p ~/.m2/repository/"$dir"
    echo "Downloading $bom..."
    curl -L -o ~/.m2/repository/"$bom" "$REPO_URL/$bom" 2>/dev/null && echo "OK" || echo "FAILED"
done

echo "Additional BOM files downloaded"
