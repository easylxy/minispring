package com.mini.controller;

import com.mini.annotation.Autowired;
import com.mini.annotation.Controller;
import com.mini.service.HelloService;

/**
 * 示例控制器类
 */
@Controller
public class HelloController {

    @Autowired
    private HelloService helloService;

    public String sayHello(String name) {
        return helloService.sayHello(name);
    }

    // 测试用getter方法
    public HelloService getHelloService() {
        return helloService;
    }
}