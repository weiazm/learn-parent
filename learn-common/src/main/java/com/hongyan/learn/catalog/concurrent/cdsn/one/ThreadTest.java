/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent.cdsn.one;

/**
 * @title ThreadTest
 * @desc description
 * @author hongyan
 * @date 2016年7月31日
 * @version version
 */
public class ThreadTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Runnable run = new Runnable() {
            Count count = new Count(0, 11);
            @Override
            public void run() {
                count.count();            
            }
        };
        
        for (int i = 0; i < 10; i++) {
            new Thread(run).start();
        }

    }

}
