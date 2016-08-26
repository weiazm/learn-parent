/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.proxy.universalProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @title UniversalHandler
 * @desc description
 * @author hongyan
 * @date 2016年8月4日
 * @version version
 */
public class UniversalHandler implements InvocationHandler {

    private Object target = null;
    private BeforeAfter ba = null;

    public UniversalHandler(Object target, BeforeAfter ba) {
        this.target = target;
        this.ba = ba;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ba.before(target, method, args);
        Object result = method.invoke(target, args);
        ba.after(target, method, args);
        return result;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(),
            this);
    }

}
