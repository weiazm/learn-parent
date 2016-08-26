/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent.cdsn.three;

/**
 * @title Main100Sub10
 * @desc description
 * @author hongyan
 * @date 2016年7月31日
 * @version version
 */
public class Main100Sub10 {
    private boolean flag = true;

    public synchronized void main(int loop) {
        while (flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 100; i++) {
            System.out.println("main:" + i + " loop:" + loop);
        }
        flag = !flag;
        this.notifyAll();
    }

    public synchronized void sub(int loop) {
        while (!flag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("sub:" + i + " loop:" + loop);
        }
        flag = !flag;
        this.notifyAll();
    }


}
