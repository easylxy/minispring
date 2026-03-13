package com.mini.context;

import com.mini.exception.BeansException;

/**
 * Spring上下文接口
 */
public interface ApplicationContext {

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
     * 关闭上下文
     */
    void close();
}