package com.mini.factory;

import com.mini.exception.BeansException;

/**
 * 可列出Bean工厂接口，提供更高级的Bean工厂功能
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 注册单例Bean
     */
    void registerSingleton(String name, Class<?> beanClass);

    /**
     * 获取指定类型的所有Bean名称
     */
    String[] getBeanNamesForType(Class<?> type);

    /**
     * 预实例化所有单例
     */
    void preInstantiateSingletons();
}