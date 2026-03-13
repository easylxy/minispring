package com.mini.factory;

/**
 * Bean定义类，包含Bean的配置信息
 */
public class BeanDefinition {

    private final Class<?> beanClass;
    private final boolean singleton;
    private final boolean prototype;

    public BeanDefinition(Class<?> beanClass) {
        this(beanClass, true);
    }

    public BeanDefinition(Class<?> beanClass, boolean singleton) {
        this.beanClass = beanClass;
        this.singleton = singleton;
        this.prototype = !singleton;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }
}