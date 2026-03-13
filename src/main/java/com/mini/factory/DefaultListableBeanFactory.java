package com.mini.factory;

import com.mini.exception.BeansException;
import com.mini.context.AbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的可列出Bean工厂
 */
public class DefaultListableBeanFactory implements ListableBeanFactory {

    // Bean定义缓存
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    // 单例缓存
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String name) throws BeansException {
        // 1. 先从单例缓存中获取
        Object bean = singletonObjects.get(name);
        if (bean != null) {
            return bean;
        }

        // 2. 获取Bean定义
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + name + "' is defined");
        }

        // 3. 如果是单例，则创建并缓存
        if (beanDefinition.isSingleton()) {
            bean = createBean(beanDefinition);
            singletonObjects.put(name, bean);
        } else if (beanDefinition.isPrototype()) {
            // 原型每次都创建新实例
            return createBean(beanDefinition);
        }

        return bean;
    }

    @Override
    public <T> T getBean(Class<T> type) throws BeansException {
        // 获取所有匹配的Bean名称
        String[] beanNames = getBeanNamesForType(type);

        if (beanNames.length == 0) {
            throw new BeansException("No bean of type '" + type.getName() + "' is defined");
        }

        if (beanNames.length > 1) {
            throw new BeansException("Expected single bean but found " + beanNames.length
                + " beans of type '" + type.getName() + "': " + String.join(", ", beanNames));
        }

        return (T) getBean(beanNames[0]);
    }

    @Override
    public <T> T getBean(String name, Class<T> type) throws BeansException {
        Object bean = getBean(name);
        if (type.isInstance(bean)) {
            return type.cast(bean);
        } else {
            throw new BeansException("Bean named '" + name + "' is not of type " + type.getName());
        }
    }

    @Override
    public boolean containsBean(String name) {
        return beanDefinitionMap.containsKey(name);
    }

    @Override
    public boolean isSingleton(String name) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + name + "' is defined");
        }
        return beanDefinition.isSingleton();
    }

    @Override
    public boolean isPrototype(String name) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + name + "' is defined");
        }
        return beanDefinition.isPrototype();
    }

    /**
     * 注册单例Bean
     */
    public void registerSingleton(String name, Class<?> beanClass) {
        BeanDefinition beanDefinition = new BeanDefinition(beanClass, true);
        beanDefinitionMap.put(name, beanDefinition);
    }

    /**
     * 获取指定类型的所有Bean名称
     */
    public String[] getBeanNamesForType(Class<?> type) {
        return beanDefinitionMap.entrySet().stream()
                .filter(entry -> type.isAssignableFrom(entry.getValue().getBeanClass()))
                .map(Map.Entry::getKey)
                .toArray(String[]::new);
    }

    /**
     * 预实例化所有单例
     */
    public void preInstantiateSingletons() {
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            if (entry.getValue().isSingleton()) {
                getBean(entry.getKey());
            }
        }
    }

    /**
     * 创建Bean实例
     */
    private Object createBean(BeanDefinition beanDefinition) {
        try {
            Object instance = beanDefinition.getBeanClass().newInstance();
            // 注入依赖
            injectDependencies(instance);
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("Failed to create bean instance for "
                + beanDefinition.getBeanClass().getName(), e);
        }
    }

    /**
     * 注入依赖
     */
    private void injectDependencies(Object bean) {
        Class<?> clazz = bean.getClass();

        // 处理字段注入
        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(com.mini.annotation.Autowired.class)) {
                com.mini.annotation.Autowired autowired = field.getAnnotation(com.mini.annotation.Autowired.class);
                field.setAccessible(true);

                try {
                    Object dependency = getBean(field.getType());
                    field.set(bean, dependency);
                } catch (IllegalAccessException e) {
                    throw new BeansException("Failed to inject dependency for field: " + field.getName(), e);
                }
            }

            if (field.isAnnotationPresent(com.mini.annotation.Value.class)) {
                com.mini.annotation.Value value = field.getAnnotation(com.mini.annotation.Value.class);
                field.setAccessible(true);

                try {
                    field.set(bean, value.value());
                } catch (IllegalAccessException e) {
                    throw new BeansException("Failed to inject value for field: " + field.getName(), e);
                }
            }
        }
    }

    // 获取Bean定义映射
    public Map<String, BeanDefinition> getBeanDefinitionMap() {
        return beanDefinitionMap;
    }
}