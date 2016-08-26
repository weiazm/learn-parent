/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent;

/**
 * @title EasyMultithread
 * @desc description
 * @author hongyan
 * @date 2016年7月27日
 * @version version
 */
public class PrintNums implements Runnable {

    private Integer start;
    private Integer end;
    private Integer sleep;

    public PrintNums(Integer start, Integer end, Integer sleep) {
        this.start = start;
        this.end = end;
        this.sleep = sleep;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            System.out.println("进程名：" + Thread.currentThread().getName() + "\t" + i);
            try {
                Thread.sleep(this.sleep);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
