package com.hongyan.learn.test.JVM;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author weihongyan
 * @description TODO
 * @date 08/11/2016
 */
public class HeapOOM {

    public static void main(String[] args) {
        List<Object> list = Lists.newArrayList();
        while(true){
            list.add(new Object());
        }
    }
}
