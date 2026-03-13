package com.mini;

import com.mini.context.AnnotationConfigApplicationContext;
import com.mini.controller.HelloController;
import com.mini.service.HelloService;

/**
 * 极简版Spring框架启动类
 */
public class MiniSpringApplication {

    public static void main(String[] args) {
        // 创建Spring容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.mini");

        // 获取Bean
        HelloController helloController = context.getBean(HelloController.class);

        // 调用方法
        String result = helloController.sayHello("World");
        System.out.println(result);

        // 关闭容器
        context.close();
    }
}