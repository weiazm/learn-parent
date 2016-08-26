/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent;

/**
 * @title Processor
 * @desc description
 * @author hongyan
 * @date 2016年8月1日
 * @version version
 */
public class GongDanProcessor {

    /**
     * @param args
     */
    public static void main(String[] args) {
        final int workStart = 1;
        int workEnd = 1001;
        final GongDan gd = // new GongDan(workStart, workEnd);

            new GongDanWithLock(workStart, workEnd);

        new Thread(new Runnable() {
            @Override
            public void run() {
                gd.process(0);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                gd.process(1);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                gd.process(2);
            }
        }).start();

    }

}
