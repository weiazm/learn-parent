/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent.cdsn.three;

/**
 * @title MainSub100
 * @desc description
 * @author hongyan
 * @date 2016年7月31日
 * @version version
 */
public class MainSub100 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        final Main100Sub10 ms = new Main100Sub10();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    ms.sub(i);
                }
            }
        }).start();
        for (int i = 0; i < 10; i++) {
            ms.main(i);
        }

        System.out.println("====================");

        final Main100Sub10Lock msl = new Main100Sub10Lock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    msl.sub(i);
                }
            }
        }).start();
        for (int i = 0; i < 10; i++) {
            msl.main(i);
        }
    }

}
