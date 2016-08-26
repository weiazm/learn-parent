/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.proxy.universalProxy;

import java.lang.reflect.Method;

/**
 * @title BeforeAfter
 * @desc description
 * @author hongyan
 * @date 2016年8月4日
 * @version version
 */
public interface BeforeAfter {

    public void before(Object o, Method method, Object[] args);

    public void after(Object o, Method method, Object[] args);

}
