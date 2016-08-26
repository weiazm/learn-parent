/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent.cdsn.three;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @title MyBlockingQueueWithLock
 * @desc description
 * @author hongyan
 * @date 2016年8月1日
 * @version version
 */
public class MyBlockingQueueWithLock<T> extends MyBlockingQueue<T> {
    Lock lock = new ReentrantLock();
    Condition fullFlag = lock.newCondition();
    Condition emptyFlag = lock.newCondition();

    public MyBlockingQueueWithLock(int limit) {
        super(limit);
    }

    @Override
    public void put(T t) {
        lock.lock();
        try {
            while (box.size() >= limit) {
                try {
                    emptyFlag.await();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            box.add(t);
            fullFlag.signalAll();
            System.out.println(Thread.currentThread().getName() + "放入了一个" + " queue大小:" + box.size());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T get() {
        lock.lock();
        try {
            while (box.size() < 1) {
                try {
                    fullFlag.await();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            T t = box.remove(0);
            System.out.println(Thread.currentThread().getName() + "得到了一个" + " queue大小:" + box.size());
            emptyFlag.signalAll();
            return t;
        } finally {
            lock.unlock();
        }
    }

}
