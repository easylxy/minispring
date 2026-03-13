package com.mini.context;

import com.mini.annotation.Autowired;
import com.mini.annotation.Component;
import com.mini.annotation.Value;
import com.mini.exception.BeansException;
import com.mini.factory.ListableBeanFactory;
import com.mini.factory.DefaultListableBeanFactory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象应用上下文
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private final ListableBeanFactory beanFactory;

    public AbstractApplicationContext() {
        this.beanFactory = (ListableBeanFactory) new DefaultListableBeanFactory();
    }

    protected ListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * 初始化容器
     */
    public void refresh() throws BeansException {
        // 1. 扫描组件
        scanComponents();

        // 2. 注册Bean
        registerBeans();

        // 3. 实例化单例Bean
        finishBeanFactoryInitialization();

        // 4. 执行Bean后置处理器
        finishRefresh();
    }

    /**
     * 扫描组件
     */
    protected abstract void scanComponents();

    /**
     * 注册Bean
     */
    protected void registerBeans() {
        // 在子类实现中完成
    }

    /**
     * 完成Bean工厂初始化
     */
    private void finishBeanFactoryInitialization() {
        getBeanFactory().preInstantiateSingletons();
    }

    /**
     * 完成刷新
     */
    private void finishRefresh() {
        // 可以在这里添加一些初始化后的处理逻辑
    }

    
    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> type) throws BeansException {
        return getBeanFactory().getBean(type);
    }

    @Override
    public <T> T getBean(String name, Class<T> type) throws BeansException {
        return getBeanFactory().getBean(name, type);
    }

    @Override
    public boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

    @Override
    public void close() {
        // 清理资源
    }
}