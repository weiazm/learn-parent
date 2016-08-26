/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.javabase;

/**
 * @title Test
 * @desc 1+1=2
 * @author hongyan
 * @date Aug 26, 2016
 * @version 1.0
 */
public class Test {
    public static void count(Integer times) {
        int number = 0;
        for (int i = times; i > 0; i /= 2) {
            for (int j = i; j > 0; j--) {
                System.out.println(number++);
            }
        }
    }
    public static void main(String[] args) {
        count(1000);
    }
}
