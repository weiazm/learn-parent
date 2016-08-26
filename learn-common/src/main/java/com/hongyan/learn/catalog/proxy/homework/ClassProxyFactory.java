/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.proxy.homework;

/**
 * @title ClassProxyFactory
 * @desc description
 * @author hongyan
 * @date 2016年8月4日
 * @version version
 */
public interface ClassProxyFactory {
    public Object createProxy(Object originalInstance, ClassProxy proxy) throws Throwable;
}
