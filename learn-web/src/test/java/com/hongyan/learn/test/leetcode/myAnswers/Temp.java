package com.hongyan.learn.test.leetcode.myAnswers;

/**
 * @author weihongyan
 * @description TODO
 * @date 16/01/2017
 */
public class Temp {
    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        long sum = 0l;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        System.out.println(sum);
        System.out.println(System.currentTimeMillis() - l);
        System.out.println(Integer.bitCount(1 ^ 4));
    }
}
