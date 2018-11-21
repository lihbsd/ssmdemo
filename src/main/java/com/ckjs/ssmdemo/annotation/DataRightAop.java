package com.ckjs.ssmdemo.annotation;

import java.lang.annotation.*;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/20 19:03
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataRightAop {
    String tableName() default "";
}