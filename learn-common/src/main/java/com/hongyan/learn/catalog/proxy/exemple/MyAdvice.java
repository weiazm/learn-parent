/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.proxy.exemple;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @title sdfdsfs
 * @desc description
 * @author hongyan
 * @date 2016年8月4日
 * @version version
 */
public class MyAdvice implements AdviceInter {  
    
    @Override
    public void afterMethod(Object target, Method method, Object[] args) {  
        System.out.println("目标对象为：" + target.getClass().getName());  
        System.out.println(method.getName() + "执行完毕！");  
    }  
  
    @Override
    public void beforeMethod(Object target, Method method, Object[] args) {  
        System.out.println(method.getName() + "开始执行");  
        if (null != args) {  
            System.out.println("参数为：" + Arrays.asList(args));  
        } else {  
            System.out.println("参数为：" + null);  
        }  
    }  
}  
