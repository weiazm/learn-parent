/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.concurrent.cdsn.seven;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @title CallableAndFuture
 * @desc description
 * @author hongyan
 * @date 2016年8月1日
 * @version version
 */
public class CallableAndFuture {

    /**
     * @param args
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Callable<String> thread = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(500);
                return "Done!";
            }
        };

        Future<String> future = threadPool.submit(thread);

        System.out.println(future.get());

        Future<String> future2 = threadPool.submit(thread);

        System.out.println(future2.get());

        Future<String> future3 = threadPool.submit(thread);

        System.out.println(future3.get());

        threadPool.shutdown();

        // 批量获取future使用CompetionService或者List<Future>
        ExecutorService threadpool = Executors.newFixedThreadPool(3);

        CompletionService<String> cs = new ExecutorCompletionService<String>(threadpool);

        for (Integer i = 0; i < 10; i++) {
            final int j = i;
            cs.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Thread.sleep(500);
                    return "执行:" + j;
                }
            });
        }

        threadpool.shutdown();// 用完记得关闭

        for (int i = 0; i < 10; i++) {
            System.out.println(true);
            String result = cs.take().get();
            System.out.println(result);
        }


    }

}
