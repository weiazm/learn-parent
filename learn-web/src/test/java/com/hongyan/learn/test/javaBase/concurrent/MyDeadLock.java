package com.hongyan.learn.test.javaBase.concurrent;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author weihongyan
 * @description TODO
 * @date 10/11/2016
 */
@Slf4j
public class MyDeadLock {
    Object lockA = new Object();
    Object lockB = new Object();

    @SneakyThrows
    public void runner1() {
        synchronized (lockA) {
            log.info("i got lockA");
            Thread.sleep(200);
            synchronized (lockB) {
                log.info("i got lockB");
            }
        }
    }

    @SneakyThrows
    public void runner2() {
        synchronized (lockB) {
            log.info("i got lockB");
            Thread.sleep(200);
            synchronized (lockA) {
                log.info("i got lockA");
            }
        }
    }

    public static void main(String[] args) {
        MyDeadLock deadLock = new MyDeadLock();

        Runnable runner1 = new Runnable() {
            @Override
            public void run() {
                deadLock.runner1();
            }
        };
        Runnable runner2 = new Runnable() {
            @Override
            public void run() {
                deadLock.runner2();
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(runner1);
        pool.submit(runner2);
        pool.shutdown();
    }

}
