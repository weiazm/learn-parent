/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.javaBase.concurrent;

import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Queues;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by weihongyan on 9/23/16.
 * 他妈的 我真是日了狗了 signalall 用成了 notifyall 搞了一个小时
 */
@Slf4j
public class IncDecByOrder {

    private static class MyWork {
        private int work = 0;
        private volatile BlockingQueue<Integer> workers = Queues.newLinkedBlockingQueue();
        private Lock lock = new ReentrantLock();
        private Condition signal = lock.newCondition();

        public MyWork() {
            workers.add(1);
            workers.add(2);
            workers.add(3);
            workers.add(4);
        }

        @SneakyThrows
        public void inc(Integer workerNum) {
            lock.lock();
            while (!workerNum.equals(workers.peek())) {
                signal.await();
            }
            this.work++;
            log.info("value:{}", work);
            workers.offer(workers.take());
            signal.signalAll();
            lock.unlock();
        }

        @SneakyThrows
        public void dec(Integer workerNum) {
            lock.lock();
            while (!workerNum.equals(workers.peek())) {
                signal.await();
            }
            this.work--;
            log.info("value:{}", work);
            workers.offer(workers.take());
            signal.signalAll();
            lock.unlock();
        }
    }

    private static class IncWorker implements Runnable {

        @Setter
        private Boolean runFlag = true;
        private Integer workerNum;
        private MyWork myWork;

        public IncWorker(MyWork myWork, Integer workerNum) {
            this.workerNum = workerNum;
            this.myWork = myWork;
        }

        @Override
        public void run() {
            while (runFlag) {
                myWork.inc(workerNum);
            }
        }
    }

    private static class DecWorker implements Runnable {

        @Setter
        private Boolean runFlag = true;
        private Integer workerNum;
        private MyWork myWork;

        public DecWorker(MyWork myWork, Integer workerNum) {
            this.workerNum = workerNum;
            this.myWork = myWork;
        }

        @Override
        public void run() {
            while (runFlag) {
                myWork.dec(workerNum);
            }
        }
    }

    public static void main(String[] args) {
        MyWork myWork = new MyWork();
        List<Runnable> threads = Lists.newArrayList();
        threads.add(new IncWorker(myWork, 1));
        threads.add(new IncWorker(myWork, 2));
        threads.add(new DecWorker(myWork, 3));
        threads.add(new DecWorker(myWork, 4));

        ExecutorService pool = Executors.newFixedThreadPool(4);
        for (Runnable worker : threads) {
            pool.submit(worker);
        }
        pool.shutdown();
    }
}
