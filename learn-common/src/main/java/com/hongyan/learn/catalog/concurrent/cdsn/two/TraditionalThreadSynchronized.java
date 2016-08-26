/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent.cdsn.two;

/**
 * @title TraditionalThreadSynchronized
 * @desc description
 * @author hongyan
 * @date 2016年7月31日
 * @version version
 */
public class TraditionalThreadSynchronized {

    /**
     * @param args
     */
    public static void main(String[] args) {
        final Outputter output = new Outputter();

        new Thread() {
            @Override
            public void run() {
                output.output("hongyan");
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                output.output("123456");
            }
        }.start();

    }

    static class Outputter {
        // Object lock = new Object();
        /**
         * @param name
         */
        public synchronized void output(String name) {
            // 锁必须是需要互斥的多个线程间的共享对象.
            // synchronized (lock) {
                for (int i = 0; i < name.length(); i++) {
                    System.out.println(name.charAt(i));
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            // }
        }
    }

}
