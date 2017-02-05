package com.hongyan.learn.test.javaBase.JVM;

import java.util.ArrayList;

/**
 * @author weihongyan
 * @description TODO
 * @date 24/11/2016
 */
public class Suger {
    public static void shit2(ArrayList<String> a) {
        System.out.println(a);
    }

    public static void shit(ArrayList<Integer> a) {
        System.out.println(a);
    }

    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 128;
        Integer f = 128;
        Long g = 3L;

        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(g == (a + b));
        System.out.println(g.equals(a + b));

    }
}
