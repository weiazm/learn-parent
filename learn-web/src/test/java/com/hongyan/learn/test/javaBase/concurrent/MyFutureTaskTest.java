/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.javaBase.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by weihongyan on 9/23/16.
 */
public class MyFutureTaskTest {
    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<String>(() -> {
            System.out.println("futureTask running...");
            Thread.sleep(4000);
            return "fuck";
        });
        ExecutorService pool = Executors.newCachedThreadPool();
//        Future<String> future = pool.submit(futureTask);

    }
}
