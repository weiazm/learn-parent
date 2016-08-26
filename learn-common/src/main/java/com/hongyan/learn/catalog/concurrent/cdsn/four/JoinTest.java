/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent.cdsn.four;

/**
 * @title JoinTest
 * @desc description
 * @author hongyan
 * @date 2016年8月1日
 * @version version
 */
public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        JoinThread t1 = new JoinThread("t1");
        JoinThread t2 = new JoinThread("t2");
        t1.start();
        t2.start();
        t1.join();// 等到t1 t2都执行完 才执行主线
        t2.join();
        System.out.println("主线程开始执行！");
    }
}

class JoinThread extends Thread {
    public JoinThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++)
            System.out.println(getName() + getId() + "执行了" + i + "次");
    }
}
