/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.javabase;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ FIELD, METHOD, })
/**
 * @title HyMark
 * @desc description
 * @author hongyan
 * @date 2016年7月27日
 * @version version
 */
public @interface HyMark {
    // 注意基本类型，()
    int num() default 0;

    boolean ifPrint() default false;

    String tag() default "";
    
}
