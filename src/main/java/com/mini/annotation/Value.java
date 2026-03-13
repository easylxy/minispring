package com.mini.annotation;

import java.lang.annotation.*;

/**
 * 值注入注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {
    String value();
}