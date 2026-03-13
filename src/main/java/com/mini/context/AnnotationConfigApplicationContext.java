package com.mini.context;

import com.mini.annotation.Component;
import com.mini.annotation.Controller;
import com.mini.annotation.Service;
import com.mini.aspect.ProxyFactory;
import com.mini.exception.BeansException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于注解的配置应用上下文
 */
public class AnnotationConfigApplicationContext extends AbstractApplicationContext {

    private final String basePackage;

    public AnnotationConfigApplicationContext(String basePackage) {
        this.basePackage = basePackage;
        this.refresh();
    }

    @Override
    protected void scanComponents() {
        try {
            List<Class<?>> componentClasses = scanComponents(basePackage);

            // 注册Bean定义
            for (Class<?> clazz : componentClasses) {
                String beanName = getBeanName(clazz);
                getBeanFactory().registerSingleton(beanName, clazz);
            }
        } catch (Exception e) {
            throw new BeansException("Failed to scan components", e);
        }
    }

    @Override
    protected void registerBeans() {
        // 这里可以注册一些系统级别的Bean
        // 例如：Environment、PropertySourcesPlaceholderConfigurer等
    }

    /**
     * 扫描指定包下的组件
     */
    private List<Class<?>> scanComponents(String packageName) throws IOException, ClassNotFoundException {
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = Thread.currentThread()
                .getContextClassLoader()
                .getResources(path);

        List<Class<?>> classes = new ArrayList<>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (resource.getProtocol().equals("file")) {
                classes.addAll(scanFiles(new File(resource.getFile()), packageName));
            }
            // 可以添加对其他协议的支持，如jar
        }

        return classes;
    }

    /**
     * 扫描文件
     */
    private List<Class<?>> scanFiles(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();

        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(scanFiles(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);

                try {
                    Class<?> clazz = Class.forName(className);
                    // 检查是否有@Controller注解（使用更具体的检查）
                    boolean hasController = clazz.isAnnotationPresent(com.mini.annotation.Controller.class);
                    boolean hasService = clazz.isAnnotationPresent(com.mini.annotation.Service.class);
                    boolean hasComponent = clazz.isAnnotationPresent(com.mini.annotation.Component.class);

                    // 检查是否是有效的组件
                    if ((hasController || hasService || hasComponent) &&
                        !clazz.isAnnotation() &&
                        !clazz.isInterface() &&
                        !className.contains("$")) {
                        classes.add(clazz);
                    }
                } catch (ClassNotFoundException e) {
                    // 忽略无法加载的类
                }
            }
        }

        return classes;
    }

    /**
     * 获取Bean名称
     */
    private String getBeanName(Class<?> clazz) {
        // 检查是否有@Component注解或其特化注解
        Component component = clazz.getAnnotation(Component.class);
        if (component != null) {
            String value = component.value();
            if (!value.isEmpty()) {
                return value;
            }
        }

        // 检查是否有@Service注解
        Service service = clazz.getAnnotation(Service.class);
        if (service != null) {
            String value = service.value();
            if (!value.isEmpty()) {
                return value;
            }
        }

        // 检查是否有@Controller注解
        Controller controller = clazz.getAnnotation(Controller.class);
        if (controller != null) {
            String value = controller.value();
            if (!value.isEmpty()) {
                return value;
            }
        }

        // 默认使用类名首字母小写
        String className = clazz.getSimpleName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }
}