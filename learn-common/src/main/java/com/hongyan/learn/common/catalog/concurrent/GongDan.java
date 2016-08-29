/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.common.catalog.concurrent;

/**
 * @title GongDan
 * @desc description
 * @author hongyan
 * @date 2016年8月1日
 * @version version
 */
public class GongDan {
    protected Integer end;
    protected Integer work;

    public GongDan(Integer work, Integer end) {
        this.work = work;
        this.end = end;
    }

    private void proces(Integer mod) {
        if (work % 3 != mod) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + ": " + work % 3 + "->" + work);
            work++;
            this.notifyAll();
        }
    }

    public synchronized void process(Integer mod) {
        while (work < end) {
            this.proces(mod);
        }
    }
}
