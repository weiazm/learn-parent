/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.proxy.exemple;

import java.lang.reflect.Method;

/**
 * @title dsf
 * @desc description
 * @author hongyan
 * @date 2016年8月4日
 * @version version
 */
public interface AdviceInter {  
    /** 
     * 目标方法执行之前 
     *  
     */  
    public void beforeMethod(Object target, Method method, Object[] args);  
  
    /** 
     * 目标方法执行之后 
     *  
     * @param target 
     *            目标对象 
     * @param method 
     *            方法 
     * @param args 
     *            参数 
     */  
    public void afterMethod(Object target, Method method, Object[] args);  
}  
