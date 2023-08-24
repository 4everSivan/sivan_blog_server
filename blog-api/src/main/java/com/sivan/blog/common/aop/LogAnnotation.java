package com.sivan.blog.common.aop;

import java.lang.annotation.*;

/**
 * 日志注解
 * @author Sivan
 * @version 1.0
 * @date 2022/3/5 16:12
 *
 * Type: 代表可以放在方法上
 * Method: 代表可以放在方法上
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operator() default "";
}
