package com.mini;

import com.mini.context.AnnotationConfigApplicationContext;
import com.mini.controller.HelloController;
import com.mini.service.HelloService;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 极简版Spring框架测试
 */
public class MiniSpringFrameworkTest {

    @Test
    public void testBasicIoC() {
        // 创建Spring容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.mini");

        // 测试获取Bean
        HelloController helloController = context.getBean(HelloController.class);
        assertNotNull(helloController);

        HelloService helloService = context.getBean(HelloService.class);
        assertNotNull(helloService);

        // 测试依赖注入
        assertSame(helloController.getHelloService(), helloService);

        // 测试功能
        String result = helloController.sayHello("Test");
        assertEquals("Hello from Mini-Spring Framework! Hello, Test!", result);

        // 关闭容器
        context.close();
    }

    @Test
    public void testAnnotationScanning() {
        // 创建Spring容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.mini");

        // 测试是否包含Bean
        assertTrue(context.containsBean("helloController"));
        assertTrue(context.containsBean("helloService"));

        // 测试按类型获取Bean
        HelloService service = context.getBean(HelloService.class);
        assertNotNull(service);

        // 测试自定义值注入
        assertEquals("Hello from Mini-Spring Framework!", service.getGreeting());

        context.close();
    }
}