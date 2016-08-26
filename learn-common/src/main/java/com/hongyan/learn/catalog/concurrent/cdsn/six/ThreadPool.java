/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent.cdsn.six;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @title ThreadPool
 * @desc description
 * @author hongyan
 * @date 2016年8月1日
 * @version version
 */
public class ThreadPool {

    Runnable thread1 = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                System.out.println(Thread.currentThread().getName() + " 执行到:" + i);
            }
        }
    };
    Runnable thread2 = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                System.out.println(Thread.currentThread().getName() + " 执行到:" + i);
            }
        }
    };
    Runnable thread3 = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                System.out.println(Thread.currentThread().getName() + " 执行到:" + i);
            }
        }
    };
    Runnable thread4 = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                System.out.println(Thread.currentThread().getName() + " 执行到:" + i);
            }
        }
    };
    Runnable thread5 = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                System.out.println(Thread.currentThread().getName() + " 执行到:" + i);
            }
        }
    };

    public static void main(String[] args) {
        ThreadPool tp = new ThreadPool();
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        threadPool.submit(tp.thread1);
        threadPool.submit(tp.thread2);
        threadPool.submit(tp.thread3);
        threadPool.submit(tp.thread4);
        threadPool.submit(tp.thread5);
        threadPool.shutdown();
    }

}
