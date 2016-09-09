/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.util;


import com.google.common.collect.Lists;
import com.hongyan.learn.common.util.myRedis.MyRedisLock;
import com.hongyan.learn.test.springConfig.SpringRedisConfig;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by weihongyan on 9/7/16.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringRedisConfig.class)
public class MyRedisLockTest {
    private static final String lockName = "weihongyan_lock";

    @Autowired
    private RedisConnectionFactory factory;

    @Test
    public void main() throws InterruptedException {
        new MyRedisLock(factory, lockName).unlock();
        List<Thread> list = Lists.newArrayList();
        List<RedisRunner> threads = Lists.newArrayList();

        for (int i = 0; i < 50; i++) {
            threads.add(new RedisRunner(new MyRedisLock(factory, lockName)));
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(50);
        for (RedisRunner thread : threads) {
            threadPool.submit(thread);
        }
        Thread.sleep(60000);//这里很坑爹,主线程需保持运行态,否则junit会回收掉context导致其他线程挂掉.
        for (RedisRunner thread : threads) {
            thread.setFlag(false);
        }
        Thread.sleep(5000);
        log.info("statastic:  lock.timesOfAccessCache:[{}], lock.timesOfSetNX:[{}]", MyRedisLock.timesOfAccessCache, MyRedisLock.timesOfSetNX);
        log.info("main thread closed!");
    }

    @Slf4j
    private static class RedisRunner implements Runnable {
        private static final long DOING_THINGS_TIME = 200L;
        private static final long BETWEEN_LOOP_TIME = 19L;
        @Setter
        Boolean flag = true;
        private MyRedisLock lock;

        public RedisRunner(MyRedisLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                while (flag) {
//                    if (lock.tryLock(/*200L, TimeUnit.MILLISECONDS*/)) {
                    lock.lock();
                    log.info("doing things========================[{}]", Thread.currentThread().getName());
                    Thread.sleep(DOING_THINGS_TIME);
                    log.info("things done!========================[{}]", Thread.currentThread().getName());
                    lock.unlock();
//                    }
                    Thread.sleep(BETWEEN_LOOP_TIME);
                }
                log.info("thread closed!-----[{}] with timesOfGetLock:{}", Thread.currentThread().getName(), lock.timesOfGetLock);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
