package com.hongyan.learn.test.javaBase.JVM;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author weihongyan
 * @description TODO
 * @date 08/11/2016
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        List<String> stringList = Lists.newArrayList();
        for (int i = 0; ; i++) {
            stringList.add(String.valueOf(i).intern());
        }
    }
}
