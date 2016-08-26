/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent.cdsn.eight;

import java.util.Random;

/**
 * @title ReadWriteTest
 * @desc description
 * @author hongyan
 * @date 2016年8月1日
 * @version version
 */
public class ReadWriteTest {

    public static void main(String[] args) {
        final ReadWriteLockData data = new ReadWriteLockData();

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    data.set(new Random().nextInt(30));
                }
            }).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    data.get();
                }
            }).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    data.set(new Random().nextInt(30));
                }
            }).start();
        }

    }

}
