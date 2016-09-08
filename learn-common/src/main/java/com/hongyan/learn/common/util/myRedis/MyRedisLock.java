/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.common.util.myRedis;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by weihongyan on 9/7/16.
 */
@Slf4j
@Getter
public class MyRedisLock implements Lock {

    private static final StringRedisSerializer serializer = new StringRedisSerializer();
    private static final String LOCK_PRE_NAME = "SYNC_REDIS_LOCK_KEY_";
    private static final Long LOCK_DEF_EXP_SECONDS = 5L;
    private static final Long PER_LOOP_LAST_MILLS = 100L;
    private final RedisConnection redisConnection;
    private final String lockName;
    private final String fullLockName;
    private final Long lockExpireMills;

    public MyRedisLock(RedisConnectionFactory factory, String lockName) {
        this(factory, lockName, LOCK_DEF_EXP_SECONDS, TimeUnit.SECONDS);
    }

    public MyRedisLock(RedisConnectionFactory factory, String lockName, Long expireTime, TimeUnit unit) {
        this.redisConnection = factory.getConnection();
        this.lockName = lockName;
        this.fullLockName = LOCK_PRE_NAME + lockName;
        this.lockExpireMills = unit.toMillis(expireTime);
    }

    @Override
    public void lock() {
        while (!setNX(fullLockName)) {
            try {
                Thread.sleep(PER_LOOP_LAST_MILLS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("i got lock!===========================================[{}]", Thread.currentThread().getName());
    }

    @Override
    public boolean tryLock() {
        if (setNX(fullLockName)) {
            log.info("i got lock!===========================================[{}]", Thread.currentThread().getName());
            return true;
        } else {
//            log.info("miss lock!---[{}]", Thread.currentThread().getName());
            return false;
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        Long startMills = System.currentTimeMillis();
        Long mills = unit.toMillis(time);
        while (mills > System.currentTimeMillis() - startMills) {
            if (setNX(fullLockName)) {
                return true;
            }
            try {
                Thread.sleep(PER_LOOP_LAST_MILLS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void unlock() {
        log.info("unlocked!========================================================[{}]", Thread.currentThread().getName());
        del(fullLockName);
    }

    /**
     * 锁不再使用,回收连接
     */
    public void distory() {
        this.redisConnection.close();
    }

    private void del(String fullLockName) {
        redisConnection.del(serializer.serialize(fullLockName));
    }

    private Boolean setNX(String fullLockName) {
        Long current = System.currentTimeMillis();
        Boolean result = redisConnection.setNX(serializer.serialize(fullLockName), serializer.serialize(String.valueOf(current)));
        if (result) {
            redisConnection.pExpireAt(serializer.serialize(fullLockName), current + lockExpireMills);//设置超时时间
        }
        return result;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("不支持使用lockInterruptibly()方法!");
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("不支持使用newCondition()方法!");
    }
}
