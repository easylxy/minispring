package com.mini.annotation;

import java.lang.annotation.*;

/**
 * 服务层注解，是@Component的特化
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Service {
    String value() default "";
}