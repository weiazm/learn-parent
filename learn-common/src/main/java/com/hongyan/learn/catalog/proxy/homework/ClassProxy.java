/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.proxy.homework;

import org.springframework.cglib.proxy.MethodProxy;

/**
 * @title ClassProxy
 * @desc description
 * @author hongyan
 * @date 2016年8月4日
 * @version version
 */
public interface ClassProxy {
    public Object intercept(MethodProxy currentMethod, Object originalInstance, Object[] args);
}
