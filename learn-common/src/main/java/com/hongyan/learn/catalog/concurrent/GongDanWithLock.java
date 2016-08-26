/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @title GongDanWithLock
 * @desc description
 * @author hongyan
 * @date 2016年8月1日
 * @version version
 */
public class GongDanWithLock extends GongDan {
    private Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();

    public GongDanWithLock(Integer work, Integer end) {
        super(work, end);
    }

    @Override
    public void process(Integer mod) {// here problem

        while (work < end) {
            lock.lock();
            this.proces(mod);
            lock.unlock();
        }
    }

    private void proces(Integer mod) {
        if (work % 3 != mod) {
            try {
                cond.await();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + ": " + work % 3 + "->" + work);
            work++;
            cond.signalAll();
            ;
        }
    }
}
