#!/bin/bash

# 编译项目
mvn clean compile

# 运行演示
echo "=== 运行极简版Spring框架演示 ==="
echo
mvn exec:java -Dexec.mainClass="com.mini.MiniSpringApplication"

echo
echo "=== 演示完成 ==="