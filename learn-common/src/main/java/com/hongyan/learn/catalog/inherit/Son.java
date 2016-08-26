/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.inherit;

/**
 * @title Son
 * @desc description
 * @author hongyan
 * @date 2016年8月4日
 * @version version
 */
public class Son extends Father {
    @SuppressWarnings("unused")
    private int age = 5;

    static {
        System.out.println("im son's static block!");
    }

    {
        System.out.println("im son's code block!");
    }

    public Son() {
        System.out.println("im son's constructor!");
    }

    public static int recur(int a, int n){
        if(n == 1)
            return a;
        else if (n % 2 == 0)
            return recur(a, n / 2) * recur(a, n / 2);
        else
            return recur(a, (n - 1) / 2) * recur(a, (n - 1) / 2) * a;
    }

    public static void main(String[] args) {
        System.out.println(recur(2, 5));
    }
}
