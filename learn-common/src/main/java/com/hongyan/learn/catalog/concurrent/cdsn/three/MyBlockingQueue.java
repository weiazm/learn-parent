/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent.cdsn.three;

import java.util.LinkedList;
import java.util.List;

/**
 * @title BlockingQueue
 * @desc description
 * @author hongyan
 * @date 2016年7月31日
 * @version version
 */
public class MyBlockingQueue<T> {
    List<T> box;
    protected Integer limit;

    public MyBlockingQueue(int limit) {
        this.limit = limit;
        this.box = new LinkedList<T>();
    }

    public synchronized void put(T t) {
        while (box.size() >= limit) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        box.add(t);
        notifyAll();
        System.out.println(Thread.currentThread().getName() + "放入了一个" + " queue大小:" + box.size());
    }

    public synchronized T get() {
        while (box.size() < 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        T t = box.remove(0);
        System.out.println(Thread.currentThread().getName() + "得到了一个" + " queue大小:" + box.size());
        notifyAll();
        return t;
    }
}
