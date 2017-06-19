package com.hongyan.learn.common.util.redis;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 基于心跳的分布式锁，依赖RedisUtil，继承RedisBaseLock
 * 
 * @title HeartbeatLock
 * @desc TODO
 * @author purerboy
 * @date 2015年9月15日
 * @version 1.0
 */
@Slf4j
public class HeartbeatLock extends RedisBaseLock {

    @Getter
    private Heartbeat heartbeat;

    public HeartbeatLock(RedisUtil redisUtil, String key) {
        this(redisUtil, key, Heartbeat.getMyHeartbeat());
    }

    /**
     * @param redisUtil
     * @param key
     * @param heartbeat
     */
    public HeartbeatLock(RedisUtil redisUtil, String key, Heartbeat heartbeat) {
        super(redisUtil, key, heartbeat.getProcName(), 0);
        this.heartbeat = heartbeat;

    }

    @Override
    protected boolean lock(final String key) throws Exception {
        boolean succ = super.lock(key);
        if (succ) {
            return succ;
        }
        if (unlock(key, value, false)) {
            succ = super.lock(key);
        }
        return succ;
    }

    @Override
    protected LockObj createLockObj() {
        LockObj obj = super.createLockObj();
        obj.setHeartbeatStartAt(heartbeat.getHeartbeatInfo().getStartAt());
        return obj;
    }

    @Override
    public Boolean unlock(final String key, final LockObj value, final boolean force) throws Exception {
        if (redisUtil.exists(key) == false) {
            return true;
        }
        if (needUnlock(key, value, force) == false) {
            return false;
        }
        return synAct(new RedisLockAction<Boolean>() {

            @Override
            public Boolean doAction(RedisUtil redisUtil) throws Exception {
                if (redisUtil.exists(key) == false) {
                    return true;
                }
                if (needUnlock(key, value, force)) {// 判断是否需要解锁
                    redisUtil.del(key);
                    log.debug("unlock, key[{}]", key);
                    return true;
                } else {
                    log.debug("unlock fail, key[{}]", key);
                    return false;
                }
            }

        }, false);
    }

    private boolean needUnlock(final String key, final LockObj value, final boolean force) throws Exception {
        if (force) {// 强制 返回true
            return true;
        }
        LockObj last = decodeLock(redisUtil.get(key));
        if (last == null || last.equals(value)
            || (last.getOwner().equals(heartbeat.getHeartbeatInfo().getProcName())
                && (LOCK_MAP.containsKey(key) == false
                    || last.getHeartbeatStartAt() != heartbeat.getHeartbeatInfo().getStartAt()))) {
            return true;
        }
        HeartbeatBaseInfo ownerHeartbeat = Heartbeat.readHeartbeatInfo(last.getOwner(), redisUtil);
        return (ownerHeartbeat == null || ownerHeartbeat.isValid() == false);
    }

}
