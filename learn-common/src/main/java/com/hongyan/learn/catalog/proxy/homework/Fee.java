/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.proxy.homework;

/**
 * @title Fee
 * @desc description
 * @author hongyan
 * @date 2016年8月4日
 * @version version
 */

public class Fee {
    public String hello() {
        return "hello fee";
    }
    public static final void main(String[] args) throws Throwable {
        ClassProxyFactory factory = new ClassProxyFactoryImpl();

        Fee fee = (Fee) factory.createProxy(new Fee(), (currentMethod, originalInstance, arg) -> {
            Object ret;
            try {
                ret = currentMethod.invokeSuper(originalInstance, args);
                return ret + " intercept 1";
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return " failed! ";
        });
        System.out.println(fee.hello());
    }
}
