package com.hongyan.learn.common.util.redis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 基于redis的分布式锁的抽象类，依赖RedisUtil
 * 
 * @title RedisBaseLock
 * @desc TODO
 * @author purerboy
 * @date 2015年9月15日
 * @version 1.0
 */
@Slf4j
public abstract class RedisBaseLock implements Lock {

    public static final int DEF_LOCK_EXPIRE = 60;
    protected static final ConcurrentHashMap<String, LockObj> LOCK_MAP = new ConcurrentHashMap<>();
    protected static final long DEF_WAIT_TIME = 100L;
    protected static final Gson gson = new Gson();
    protected static final Type LOCK_OBJ_TYPE = new TypeToken<LockObj>() {
    }.getType();
    private static final String SYN_PREFIX = "syn_";
    private static final int SYN_EXPIRE = 5;
    protected RedisUtil redisUtil;
    protected String key;// 锁的唯一标识
    protected String owner;// 锁的拥有者
    protected int lockExpire;// 加锁过期时间(防止解锁失败)，单位:秒，<=0时，无过期时间
    protected LockObj value; // 锁的值
    private String synKey;// 同步锁

    protected RedisBaseLock(RedisUtil redisUtil, String key, String owner) {
        this(redisUtil, key, owner, DEF_LOCK_EXPIRE);
    }

    /**
     *
     * @param redisUtil redisUtil中包含redis连接池
     * @param key 锁的唯一标识
     * @param owner 锁的拥有者
     * @param lockExpire 加锁过期时间(防止unlock失败) 单位:秒，<=0时，无过期时间
     */
    protected RedisBaseLock(RedisUtil redisUtil, String key, String owner, int lockExpire) {
        super();
        this.redisUtil = redisUtil;
        this.key = key;
        this.owner = owner;
        this.lockExpire = lockExpire;
        this.synKey = SYN_PREFIX + key;
    }

    protected <T> T synAct(RedisLockAction<T> action, T def) throws Exception {
        boolean syn = false;
        try {
            syn = redisUtil.setnx(synKey, owner, SYN_EXPIRE) == RedisUtil.OK_RESULT;
            if (syn) {
                return action.doAction(redisUtil);
            }
            return def;
        } finally {
            if (syn) {
                redisUtil.del(synKey);
            }
        }
    }

    @Override
    public void lock() {
        tryLock(-1, null);
    }

    @Deprecated
    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("不支持的方法!");
    }

    @Override
    public boolean tryLock() {
        return tryLock(0, null);
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) {
        long start = System.nanoTime();
        try {
            do {
                if (lock(key)) {
                    if (log.isDebugEnabled()) {
                        log.debug("get lock, key[{}], owner[{}], expire[{}]", key, owner, lockExpire);
                    }
                    return true;
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("key[{}] is locked by owner[{}]", key, redisUtil.get(key));
                    }
                }
                if (timeout == 0) {
                    break;
                }
                Thread.sleep(DEF_WAIT_TIME);
            } while (timeout < 0 || (System.nanoTime() - start) < unit.toNanos(timeout));
            return false;
        } catch (Exception e) {
            log.error("lock key[" + key + "] error!", e);
            return false;
        }
    }

    protected boolean lock(final String key) throws Exception {
        boolean succ = false;
        LockObj obj = createLockObj();
        LockObj last = LOCK_MAP.putIfAbsent(key, obj);
        if (lockExpire > 0) {
            if (last != null) {
                if (System.currentTimeMillis() - last.getTime() < lockExpire) {
                    return false;
                } else {
                    LOCK_MAP.remove(key, last);
                    last = LOCK_MAP.putIfAbsent(key, obj);
                    if (last != null) {
                        return false;
                    }
                }
            }
            succ = redisUtil.setnx(key, encodeLock(obj), lockExpire) == RedisUtil.OK_RESULT;
        } else {
            if (last != null) {
                return false;
            }
            try {
                succ = redisUtil.setnx(key, encodeLock(obj)) == RedisUtil.OK_RESULT;
            } catch (Exception e) {
                LOCK_MAP.remove(key, obj);
                throw e;
            }
        }
        if (succ) {
            value = obj;
        } else {
            LOCK_MAP.remove(key, obj);
        }
        return succ;
    }

    protected LockObj createLockObj() {
        LockObj obj = new LockObj();
        obj.setOwner(owner);
        obj.setTime(System.currentTimeMillis());
        return obj;
    }

    /**
     * <p>
     * 为避免锁的拥有者挂掉导致无法解锁，非锁的所有者也可以解锁
     * </p>
     * {@inheritDoc}
     */
    @Override
    public void unlock() {
        try {
            unlock(true);
        } catch (Exception e) {
            log.error("release lock key[" + key + "] error!", e);
        }
    }

    public void unlock(boolean force) {
        try {
            unlock(key, value, force);
        } catch (Exception e) {
            log.error("release lock key[" + key + "] error!", e);
        } finally {
            if (force) {
                LockObj obj = LOCK_MAP.get(key);
                LOCK_MAP.remove(key, obj);
            } else {
                LOCK_MAP.remove(key, value);
            }
        }
    }

    abstract public Boolean unlock(final String key, final LockObj value, final boolean force) throws Exception;

    @Deprecated
    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("不支持的方法!");
    }

    protected LockObj decodeLock(String json) {
        return gson.fromJson(json, LOCK_OBJ_TYPE);
    }

    protected String encodeLock(LockObj obj) {
        return gson.toJson(obj);
    }

    protected interface RedisLockAction<T> {
        T doAction(RedisUtil redisUtil) throws Exception;
    }
}
