/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.proxy.csdn.compilerAPI;

/**
 * @title MyClassLoader
 * @desc description
 * @author hongyan
 * @date 2016年8月9日
 * @version 1.0
 */
public class MyClassLoader extends ClassLoader {
    public Class<?> myLoad(String name, byte[] b, int off, int len) {
        return super.defineClass(name, b, off, len);
    }
}
