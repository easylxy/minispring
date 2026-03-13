package com.mini.annotation;

import java.lang.annotation.*;

/**
 * 组件注解，用于标记需要被Spring管理的类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}