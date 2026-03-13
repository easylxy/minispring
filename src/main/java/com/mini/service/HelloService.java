package com.mini.service;

import com.mini.annotation.Service;
import com.mini.annotation.Value;

/**
 * 示例服务类
 */
@Service
public class HelloService {

    @Value("Hello from Mini-Spring Framework!")
    private String greeting;

    public String sayHello(String name) {
        return greeting + " Hello, " + name + "!";
    }

    // 测试用getter方法
    public String getGreeting() {
        return greeting;
    }
}