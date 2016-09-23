/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.util.curator;

import com.google.common.collect.Lists;
import com.hongyan.learn.config.ZookeeperCuratorConfig;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by weihongyan on 9/14/16.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ZookeeperCuratorConfig.class)
public class CuratorLockTest {

    @Value("${zookeeper.lockDir}")
    private String lockDir;

    @Autowired
    private CuratorFramework client;

    @Test
    @SneakyThrows
    @Ignore
    public void test() {
        client.start();

        InterProcessMutex lock = new InterProcessMutex(client, lockDir);
        lock.acquire();//死锁了

        List<ZookeeperRunner> threads = Lists.newArrayList();
        for (int i = 0; i < 50; i++) {
            threads.add(new ZookeeperRunner(lock));
        }
        log.info("threads prepare done!");
        ExecutorService threadPool = Executors.newFixedThreadPool(50);
        for (ZookeeperRunner thread : threads) {
            threadPool.submit(thread);
        }
        log.info("threads start done!");
        Thread.sleep(60000);//这里很坑爹,主线程需保持运行态,否则junit会回收掉context导致其他线程挂掉.
        for (ZookeeperRunner thread : threads) {
            thread.setFlag(false);
        }
        log.info("send stop signal done!");
        Thread.sleep(50000);
        log.info("main thread closed!");

        client.close();
    }

    @Slf4j
    private static class ZookeeperRunner implements Runnable {
        private static final long DOING_THINGS_TIME = 200L;
        private static final long BETWEEN_LOOP_TIME = 19L;
        @Setter
        Boolean flag = true;
        private InterProcessMutex lock;

        public ZookeeperRunner(InterProcessMutex lock) {
            this.lock = lock;
        }

        @Override
        @SneakyThrows
        public void run() {
            while (flag) {
                lock.acquire();
                log.info("doing things========================[{}]", Thread.currentThread().getName());
                Thread.sleep(DOING_THINGS_TIME);
                log.info("things done!========================[{}]", Thread.currentThread().getName());
                lock.release();
                Thread.sleep(BETWEEN_LOOP_TIME);
            }
            log.info("thread closed!-----[{}]", Thread.currentThread().getName());
        }
    }
}
