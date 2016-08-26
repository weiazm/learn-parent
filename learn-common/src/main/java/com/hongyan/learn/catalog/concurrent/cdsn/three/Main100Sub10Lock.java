/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent.cdsn.three;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @title Main100Sub10Lock
 * @desc description
 * @author hongyan
 * @date 2016年8月1日
 * @version version
 */
public class Main100Sub10Lock {
    private boolean flag = true;
    private Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();

    public void main(int loop) {
        lock.lock();
        while (flag) {
            try {
                cond.await();
                ;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 100; i++) {
            System.out.println("main:" + i + " loop:" + loop);
        }
        flag = !flag;
        cond.signalAll();
        lock.unlock();
    }

    public  void sub(int loop) {
        lock.lock();
        while (!flag) {
            try {
                cond.await();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("sub:" + i + " loop:" + loop);
        }
        flag = !flag;
        cond.signalAll();
        lock.unlock();
    }
}
