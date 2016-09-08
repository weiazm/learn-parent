/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.common.util.myRedis;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
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
    private static final Long LOCK_DEF_EXP_SECONDS = 60L;//TODO 没用到
    private final RedisConnection redisConnection;
    private final String lockName;
    private final String fullLockName;
    private final Long lockExpireSeconds;

    public MyRedisLock(RedisConnection redisConnection, String lockName) {
        this(redisConnection, lockName, LOCK_DEF_EXP_SECONDS, TimeUnit.SECONDS);
    }

    public MyRedisLock(RedisConnection redisConnection, String lockName, Long expireTime, TimeUnit unit) {
        this.redisConnection = redisConnection;
        this.lockName = lockName;
        this.fullLockName = LOCK_PRE_NAME + lockName;
        this.lockExpireSeconds = unit.toSeconds(expireTime);
    }

    @Override
    public void lock() {
        while (setNX(fullLockName)) {
            //TODO 死循环
        }
    }

    @Override
    public boolean tryLock() {
        return setNX(fullLockName);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        Long startMills = System.currentTimeMillis();
        Long mills = unit.toMillis(time);
        while (mills > System.currentTimeMillis() - startMills) {
            if (setNX(fullLockName)) {
                return true;
            }
            //TODO 反复循环
        }
        return false;
    }

    @Override
    public void unlock() {
        try {
            del(fullLockName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void del(String fullLockName) {
        redisConnection.del(serializer.serialize(fullLockName));
    }

    private Boolean setNX(String fullLockName) {
        return redisConnection.setNX(serializer.serialize(fullLockName), serializer.serialize(String.valueOf(System.currentTimeMillis())));
    }

    private String getSet(String fullLockName) {
        byte[] bytes = redisConnection.getSet(serializer.serialize(fullLockName), serializer.serialize(String.valueOf(System.currentTimeMillis())));
        return serializer.deserialize(bytes);
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
