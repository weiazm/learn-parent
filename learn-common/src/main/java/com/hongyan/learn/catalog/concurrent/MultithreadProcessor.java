/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @title MultithreadWork
 * @desc description
 * @author hongyan
 * @date 2016年7月27日
 * @version version
 */
public class MultithreadProcessor implements Runnable {

    private LinkedBlockingQueue<Integer> workQueue;
    private Integer mod;
    private Integer delay;

    public MultithreadProcessor(LinkedBlockingQueue<Integer> workQueue, Integer mod, Integer delay) {
        this.workQueue = workQueue;
        this.mod = mod;
        this.delay = delay;
    }

    @Override
    public void run() {
        while (!workQueue.isEmpty()) {


            Integer top = workQueue.peek();
            System.out.println(Thread.currentThread().getName() + "peek()");
            // 注意队列的peek，队列为空时peek值为null
            if (null != top && top % 3 == mod) {

                System.out.println(Thread.currentThread().getName() + "\t\t" + workQueue.poll());
                try {
                    Thread.sleep((long) (Math.random() * this.delay));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else {

            }

        }
    }

}
