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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by weihongyan on 9/7/16.
 */
//paxos zk 存在不同机器上的系统时间不一致问题
@Slf4j
@Getter
public class MyRedisLock implements Lock {
    private static final ConcurrentHashMap<String, Long> lockCache = new ConcurrentHashMap<String, Long>();//用于减轻redis压力
    private static final StringRedisSerializer serializer = new StringRedisSerializer();
    private static final String LOCK_PRE_NAME = "SYNC_REDIS_LOCK_KEY_";
    private static final Long LOCK_DEF_EXP_SECONDS = 5L;
    private static final Long PER_LOOP_LAST_MILLS = 10L;
    public static volatile int timesOfSetNX = 0;
    public static volatile int timesOfAccessCache = 0;
    private final RedisConnection redisConnection;
    private final String lockName;
    private final String fullLockName;
    private final Long lockExpireMills;
    private final byte[] serializedKey;
    public int timesOfGetLock = 0;

    /**
     * 以线程为实例单位,构造器需要传入两个参数.依赖spring-data-redis,lombok包.
     *
     * @param factory  spring中的redis连接工厂.
     * @param lockName 以字符串来区分远程redis锁,相同字符串代表同一把锁.
     */
    public MyRedisLock(RedisConnectionFactory factory, String lockName) {
        this(factory, lockName, LOCK_DEF_EXP_SECONDS, TimeUnit.SECONDS);
    }

    public MyRedisLock(RedisConnectionFactory factory, String lockName, Long expireTime, TimeUnit unit) {
        this.redisConnection = factory.getConnection();
        this.lockName = lockName;
        this.fullLockName = LOCK_PRE_NAME + lockName;
        this.lockExpireMills = unit.toMillis(expireTime);
        this.serializedKey = serializer.serialize(this.fullLockName);
    }

    @Override
    public void lock() {
        try {
            this.tryLock(-1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean tryLock() {
        try {
            return this.tryLock(0, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        Long startMills = System.currentTimeMillis();
        Long mills = unit.toMillis(time);
        do {
            if (setNX(fullLockName)) {
                timesOfGetLock++;
                log.info("i got lock!===========================================[{}]", Thread.currentThread().getName());
                return true;
            }
            Thread.sleep(PER_LOOP_LAST_MILLS);
        } while (time < 0 || System.currentTimeMillis() < mills + startMills);
//        log.info("miss lock!---[{}]", Thread.currentThread().getName());
        return false;
    }

    @Override
    public void unlock() {
        log.info("unlocked!========================================================[{}]", Thread.currentThread().getName());
        del(fullLockName);
    }

    private void del(String fullLockName) {
        redisConnection.del(serializedKey);
        lockCache.remove(fullLockName);
    }

    private Boolean setNX(String fullLockName) {
        Long lastLockTime = lockCache.get(fullLockName);
        if (null != lastLockTime && System.currentTimeMillis() < lastLockTime + lockExpireMills) {//还没到过期时间
            timesOfAccessCache++;
            return false;
        }
        Long current = System.currentTimeMillis();
        Boolean result = redisConnection.setNX(serializedKey, serializer.serialize(String.valueOf(current)));
        if (result) {
            lockCache.put(fullLockName, current);//更新到本地缓存
            redisConnection.pExpireAt(serializedKey, current + lockExpireMills);//设置超时时间
        } else {
            Long expireMills = redisConnection.pTtl(serializedKey);//保险措施
            if (expireMills == -1) {//说明这个key没设置超时时间
                lockCache.put(fullLockName, current);//更新到本地缓存
                redisConnection.pExpireAt(serializedKey, current + lockExpireMills);//设置超时时间
            }
        }
        timesOfSetNX++;
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
