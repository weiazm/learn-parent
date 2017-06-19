package com.hongyan.learn.common.util.redis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import lombok.extern.slf4j.Slf4j;

/**
 * 基于redis的分布式锁的抽象类，依赖RedisUtil 可以创建个单例工厂类,用于针对相同的key,返回相同的lock.
 *
 * @author purerboy
 * @version 1.0
 * @title RedisBaseLock
 * @desc TODO
 * @date 2015年9月15日
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
     * @param redisUtil redisUtil中包含redis连接池
     * @param key 锁的唯一标识
     * @param owner 锁的拥有者
     * @param lockExpire 加锁过期时间(防止unlock失败) 单位:秒，<=0时，无过期时间
     */
    protected RedisBaseLock(RedisUtil redisUtil, String key, String owner, int lockExpire) {
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

    /**
     * 获取锁,未能获取则等待
     */
    @Override
    public void lock() {
        tryLock(-1, null);
    }

    @Deprecated
    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("不支持的方法!");
    }

    /**
     * 尝试不等待获取锁
     *
     * @return 是否获取到了锁
     */
    @Override
    public boolean tryLock() {
        try {
            return lock(key);
        } catch (Exception e) {
            log.error("lock key[" + key + "] error!", e);
            return false;
        }
    }

    /**
     * 尝试在一定时间内获取锁
     *
     * @param timeout 时间长度
     * @param unit 时间单位
     * @return 是否获取到了锁
     */
    @Override
    public boolean tryLock(long timeout, TimeUnit unit) {
        long start = System.nanoTime();
        try {
            do {
                if (lock(key)) {
                    log.debug("get lock, key[{}], owner[{}], expire[{}]", key, owner, lockExpire);
                    return true;
                } else {
                    log.debug("key[{}] is locked by owner[{}]", key, redisUtil.get(key));
                }
                Thread.sleep(DEF_WAIT_TIME);// 循环间隔毫秒
                // timeout小于0,一直等待循环获取锁,超时时间内一直循环获取锁.
            } while (timeout < 0 || (System.nanoTime() - start) < unit.toNanos(timeout));
            return false;
        } catch (Exception e) {
            log.error("lock key[" + key + "] error!", e);
            return false;
        }
    }

    /**
     * 将锁的时间戳保存在本地的map中,每次查询上锁时间时不必使用redis来查询,挡掉了一些对redis的访问. 单redis客户端中map中的值与远程的redis中的锁一致.
     * 多redis客户端使用相同锁时,map为redis锁的子集.
     *
     * @param key 锁的字符串key
     * @return 获取锁是否成功
     * @throws Exception
     */
    protected boolean lock(final String key) throws Exception {
        boolean succ = false;
        LockObj obj = createLockObj();// 锁对象
        LockObj last = LOCK_MAP.putIfAbsent(key, obj);// map里没有再放入,返回null;map里有则不更新,返回原来的值,(返回null或redis中保存的锁)
        if (lockExpire > 0) {// 如果指定了超时时间
            if (last != null) {// 如果redis锁没有被释放
                if (System.currentTimeMillis() - last.getTime() < lockExpire) {// 锁还未超时,返回false.
                    return false;
                } else {// 锁超时了
                    LOCK_MAP.remove(key, last);// 删掉超时的锁
                    last = LOCK_MAP.putIfAbsent(key, obj);// 更新当前的新锁
                    if (last != null) {// 冗余操作,这步肯定是空
                        return false;
                    }
                    try {
                        succ = redisUtil.setnx(key, encodeLock(obj), lockExpire) == RedisUtil.OK_RESULT;// 更新远程redis的新锁
                    } catch (Exception e) {
                        LOCK_MAP.remove(key, obj);// 发生异常失败了,锁没更新上
                        throw e;
                    }
                }
            }
        } else {// 如果超时时间无限期
            if (last != null) {// 如果redis锁没有被释放,返回false
                return false;
            } else {// redis中的锁被释放了
                try {
                    succ = redisUtil.setnx(key, encodeLock(obj)) == RedisUtil.OK_RESULT;// 更新远程redis的新锁
                } catch (Exception e) {
                    LOCK_MAP.remove(key, obj);// 发生异常失败了,锁没更新上
                    throw e;
                }
            }
        }
        if (succ) {
            value = obj;
        } else {
            // 单机冗余操作,理论上用不到,万一以为超时了实际未超时,这步可以纠错,保证map与redis中的锁一致.
            // 当有并发redis客户端获取相同锁的情况产生时,这句能保证map是redis中的锁的子集
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
            if (force) {// 强制解锁会强制移除map中对应key的锁
                LockObj obj = LOCK_MAP.get(key);
                LOCK_MAP.remove(key, obj);
            } else {// 非强制解锁会尝试移除对应的key-value
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
