/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent.cdsn.three;

/**
 * @title QueueTest
 * @desc description
 * @author hongyan
 * @date 2016年7月31日
 * @version version
 */
public class QueueTest {

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // final MyBlockingQueue<Integer> queue = new MyBlockingQueue<Integer>(9);
        final MyBlockingQueue<Integer> queue = new MyBlockingQueueWithLock<Integer>(3);

        class Producer implements Runnable {
            private Integer num;
            public Producer(Integer num) {
                this.num = num;
            }
            @Override
            public void run() {
                try {
                    Thread.sleep(11);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                for (int i = 0; i < num; i++)
                    queue.put(1);
            }
        }

        class Consumer implements Runnable {
            private Integer num;
            public Consumer(Integer num) {
                this.num = num;
            }
            @Override
            public void run() {
                try {
                    Thread.sleep(12);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                for (int i = 0; i < num; i++)
                    queue.get();
            }
        }

        new Thread(new Consumer(200)).start();
        new Thread(new Producer(100)).start();
        new Thread(new Producer(100)).start();
        Thread.sleep(1000);
        System.out.println(queue.box.size());
    }

}
