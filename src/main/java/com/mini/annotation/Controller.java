package com.mini.annotation;

import java.lang.annotation.*;

/**
 * 控制器注解，是@Component的特化
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {
    String value() default "";
}