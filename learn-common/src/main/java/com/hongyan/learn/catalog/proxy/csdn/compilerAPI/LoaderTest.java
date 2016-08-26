/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.proxy.csdn.compilerAPI;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * @title CompilerTest
 * @desc description
 * @author hongyan
 * @date 2016年8月8日
 * @version 1.0
 */
public class LoaderTest {
    public static void main(String[] args) throws Throwable {
        String classFile = "/Users/hongyan/Documents/workspace/learn/target/"
            + "classes/com/hongyan/learn/proxy/csdn/compilerAPI/然儿.class";
        byte[] b = new byte[10240];
        FileInputStream in = new FileInputStream(new File(classFile));
        int len = in.read(b);
        in.close();
        Class<?> clazz = new MyClassLoader().myLoad(null, b, 0, len);
        Object o = clazz.newInstance();
        
        Method[] ms = clazz.getMethods();
        for (Method m : ms) {
            System.out.println(m.getName());
        }

        Method m = clazz.getMethod("健身");
        m.invoke(o);
    }
}