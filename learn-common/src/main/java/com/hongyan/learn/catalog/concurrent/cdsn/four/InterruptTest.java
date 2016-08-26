/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent.cdsn.four;

/**
 * @title InterruptTest
 * @desc description
 * @author hongyan
 * @date 2016年8月1日
 * @version version
 */
public class InterruptTest {
    static class MyThread extends Thread {
        @Override
        public void run() {
            while (!this.isInterrupted()) {
                System.out.println("loop");
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new MyThread();
        t.start();
        Thread.sleep(1000);
        t.interrupt();// 只有Thread有该中断标志.
    }

}
