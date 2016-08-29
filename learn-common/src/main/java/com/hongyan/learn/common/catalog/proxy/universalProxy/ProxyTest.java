/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.common.catalog.proxy.universalProxy;

import sun.misc.ProxyGenerator;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @title ProxyTest
 * @desc description
 * @author hongyan
 * @date 2016年8月4日
 * @version version
 */
@SuppressWarnings("restriction")
public class ProxyTest {

    public static void main(String[] args) throws Throwable {

        UniversalHandler handler = new UniversalHandler(new ArrayList<Integer>(), new BeforeAfter() {
            @Override
            public void before(Object o, Method method, Object[] args) {
                System.out.println("before");
            }

            @Override
            public void after(Object o, Method method, Object[] args) {
                System.out.println("after");
            }
        });

        @SuppressWarnings("unchecked")
        List<Integer> list = (List<Integer>) handler.getProxy();// 这里只能强转为接口类型,而且必须有接口

        list.add(1);
        System.out.println(list);

        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy11", ArrayList.class.getInterfaces());
        BufferedOutputStream bos =
            new BufferedOutputStream(new FileOutputStream("/Users/hongyan/Desktop/$Proxy11.class"));
        bos.write(classFile);
        bos.close();

    }

}
