# 极简版Spring框架 (Mini-Spring Framework)

这是一个极简版的Spring框架实现，用于帮助理解Spring框架的核心原理。

## 项目结构

```
mini-spring-framework/
├── src/main/java/com/mini/
│   ├── MiniSpringApplication.java          # 启动类
│   ├── annotation/                        # 注解定义
│   │   ├── Component.java
│   │   ├── Controller.java
│   │   ├── Service.java
│   │   ├── Autowired.java
│   │   └── Value.java
│   ├── context/                          # Spring容器
│   │   ├── ApplicationContext.java
│   │   ├── AbstractApplicationContext.java
│   │   └── AnnotationConfigApplicationContext.java
│   ├── factory/                          # Bean工厂
│   │   ├── BeanFactory.java
│   │   ├── ListableBeanFactory.java
│   │   ├── DefaultListableBeanFactory.java
│   │   └── BeanDefinition.java
│   ├── aspect/                           # AOP支持
│   │   └── ProxyFactory.java
│   ├── controller/                       # 示例控制器
│   │   └── HelloController.java
│   ├── service/                          # 示例服务
│   │   └── HelloService.java
│   └── exception/                        # 异常类
│       └── BeansException.java
├── src/test/java/com/mini/
│   └── MiniSpringFrameworkTest.java
└── pom.xml
```

## 核心特性

1. **IoC (控制反转)**：通过容器管理Bean的生命周期
2. **DI (依赖注入)**：支持字段注入
3. **注解驱动**：支持@Component、@Service、@Controller等注解
4. **依赖查找**：通过名称或类型获取Bean
5. **AOP支持**：基础的动态代理实现
6. **单例模式**：支持单例Bean

## 核心原理

### 1. 容器 (ApplicationContext)

Spring的核心是容器，负责：
- Bean的创建和管理
- 依赖注入
- 生命周期管理

### 2. Bean工厂 (BeanFactory)

提供基本的Bean获取功能，是Spring IoC容器的基础接口。

### 3. Bean定义 (BeanDefinition)

描述Bean的配置信息，包括：
- Bean的类型
- 是否单例
- 作用域等

### 4. 依赖注入 (DI)

支持多种注入方式：
- 字段注入 (@Autowired)
- 构造器注入
- Setter注入

### 5. 组件扫描

通过反射扫描指定包下的类，查找带有@Component注解的类，并注册为Bean。

## 运行示例

### 1. 编译项目

```bash
mvn clean compile
```

### 2. 运行测试

```bash
mvn test
```

### 3. 运行示例

```bash
java -cp target/classes com.mini.MiniSpringApplication
```

## 输出结果

```
Before method: sayHello
Hello from Mini-Spring Framework! Hello, World!
After method: sayHello
```

## 设计模式

这个极简版框架使用了以下设计模式：

1. **工厂模式**：BeanFactory创建和管理Bean
2. **单例模式**：单例Bean的缓存
3. **代理模式**：JDK动态代理实现AOP
4. **模板方法**：AbstractApplicationContext定义刷新流程

## 扩展建议

1. **添加@Configuration支持**：支持配置类
2. **实现@Qualifier**：支持限定符注入
3. **添加生命周期回调**：实现InitializingBean和DisposableBean
4. **支持@Scope**：支持不同的Bean作用域
5. **实现基于XML的配置**：支持XML配置文件

## 与Spring的区别

这个极简版框架相比完整的Spring框架缺少：

1. 完整的生命周期管理
2. 事务管理
3. 事件机制
4. 条件化Bean注册
5. 基于Java配置的高级功能（如@Configuration）
6. Spring Boot的自动配置
7. 原型Bean的完整支持
8. 复杂的依赖注入（如构造器注入、Setter注入）

## 总结

这个极简版Spring框架展示了Spring的核心概念：
- **IoC**：控制反转，由容器管理对象
- **DI**：依赖注入，减少组件间的耦合
- **面向切面编程**：通过代理实现横切关注点

通过理解这个实现，可以更好地理解Spring框架的工作原理。