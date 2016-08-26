/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.proxy.homework;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @title CgLibTest
 * @desc description
 * @author hongyan
 * @date 2016年8月9日
 * @version 1.0
 */
public class CgLibTest {
    public static class Singer {
        public void sing() {
            System.out.println("im singing");
        }
    }

    public static class MyInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
            return arg3.invokeSuper(arg0, arg2);
        }
    }
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        MyInterceptor interceptor = new MyInterceptor();
        enhancer.setSuperclass(Singer.class);
        enhancer.setCallback(interceptor);
        Singer singer = (Singer) enhancer.create();
        singer.sing();
    }

}
