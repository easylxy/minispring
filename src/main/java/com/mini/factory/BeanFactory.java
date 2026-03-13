package com.mini.factory;

import com.mini.exception.BeansException;

/**
 * Bean工厂接口
 */
public interface BeanFactory {

    /**
     * 根据名称获取Bean
     */
    Object getBean(String name) throws BeansException;

    /**
     * 根据类型获取Bean
     */
    <T> T getBean(Class<T> type) throws BeansException;

    /**
     * 根据名称和类型获取Bean
     */
    <T> T getBean(String name, Class<T> type) throws BeansException;

    /**
     * 判断是否包含指定名称的Bean
     */
    boolean containsBean(String name);

    /**
     * 判断是否是单例
     */
    boolean isSingleton(String name) throws BeansException;

    /**
     * 判断是否是原型
     */
    boolean isPrototype(String name) throws BeansException;
}