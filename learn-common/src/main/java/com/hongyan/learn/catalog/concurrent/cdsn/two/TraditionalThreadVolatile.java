/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent.cdsn.two;

/**
 * @title TraditionalThreadVolatile
 * @desc description
 * @author hongyan
 * @date 2016年7月31日
 * @version version
 */
public class TraditionalThreadVolatile {

    /**
     * @param args
     */
    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                Test.one();
            }
        }.start();
        new Thread(){
            @Override
            public void run(){
                Test.two();
            }
        }.start();

    }

    static class Test {
        static volatile int i = 0;// volatile保证了变量的可见性,保证不了有序性
        static volatile int j = 0;

        // 锁在静态方法上,相当于锁住了这个类
        static /* synchronized */ void one() {
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            j++;
        }

        static /* synchronized */ void two() {
            System.out.println(i + ":" + j);
        }
    }

}
