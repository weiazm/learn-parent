/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.common.catalog.proxy;

import com.google.common.collect.Lists;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @title MyReflectProxy
 * @desc description
 * @author hongyan
 * @date 2016年8月4日
 * @version version
 */
public class MyReflectProxy {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) {

        List list = (List) Proxy.newProxyInstance(List.class.getClassLoader(), new Class[] { List.class },
            new InvocationHandler() {
                private ArrayList<Object> target = Lists.newArrayList();

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("before");
                    Object result = method.invoke(target, args);
                    System.out.println("after");
                    return result;
                }
            });

        list.add(new Object());

    }

}
