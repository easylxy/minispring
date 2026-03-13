package com.mini.aspect;

import com.mini.exception.BeansException;
import com.mini.factory.ListableBeanFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单的代理工厂，支持JDK动态代理
 */
public class ProxyFactory {

    private final ListableBeanFactory beanFactory;
    private final Map<String, Object> proxyCache = new ConcurrentHashMap<>();

    public ProxyFactory(ListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 创建代理
     */
    public Object createProxy(Class<?> targetClass) {
        String cacheKey = targetClass.getName();

        // 检查缓存
        if (proxyCache.containsKey(cacheKey)) {
            return proxyCache.get(cacheKey);
        }

        // 创建代理
        Object proxy = Proxy.newProxyInstance(
                targetClass.getClassLoader(),
                targetClass.getInterfaces(),
                new ProxyInvocationHandler(targetClass)
        );

        // 缓存代理
        proxyCache.put(cacheKey, proxy);
        return proxy;
    }

    /**
     * 代理调用处理器
     */
    private class ProxyInvocationHandler implements InvocationHandler {

        private final Class<?> targetClass;

        public ProxyInvocationHandler(Class<?> targetClass) {
            this.targetClass = targetClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 这里可以添加切面逻辑，如日志、事务等
            System.out.println("Before method: " + method.getName());

            // 获取目标对象并调用方法
            Object target = beanFactory.getBean(getBeanName(targetClass));

            if (target == null) {
                throw new BeansException("Target bean not found: " + targetClass.getName());
            }

            Object result = method.invoke(target, args);

            // 后置处理
            System.out.println("After method: " + method.getName());

            return result;
        }

        private String getBeanName(Class<?> clazz) {
            String className = clazz.getSimpleName();
            return className.substring(0, 1).toLowerCase() + className.substring(1);
        }
    }
}