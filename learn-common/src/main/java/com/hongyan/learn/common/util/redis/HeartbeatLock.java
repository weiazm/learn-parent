package com.hongyan.learn.common.util.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于心跳的分布式锁，依赖RedisUtil，继承RedisBaseLock
 * 
 * @title HeartbeatLock
 * @desc TODO
 * @author purerboy
 * @date 2015年9月15日
 * @version 1.0
 */
public class HeartbeatLock extends RedisBaseLock {

    private static final Logger log = LoggerFactory.getLogger(HeartbeatLock.class);

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

    public Heartbeat getHeartbeat() {
        return heartbeat;
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
                if (needUnlock(key, value, force)) {
                    redisUtil.del(key);
                    if (log.isDebugEnabled()) {
                        log.debug("unlock, key[{}]", key);
                    }
                    return true;
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("unlock fail, key[{}]", key);
                    }
                    return false;
                }
            }

        }, false);
    }

    private boolean needUnlock(final String key, final LockObj value, final boolean force) throws Exception {
        if (force) {
            return true;
        }
        LockObj last = decodeLock(redisUtil.get(key));
        if (last == null
            || last.equals(value)
            || (last.getOwner().equals(heartbeat.getHeartbeatInfo().getProcName()) && (LOCK_MAP.containsKey(key) == false || last
                .getHeartbeatStartAt() != heartbeat.getHeartbeatInfo().getStartAt()))) {
            return true;
        }
        HeartbeatBaseInfo ownerHeartbeat = Heartbeat.readHeartbeatInfo(last.getOwner(), redisUtil);
        return (ownerHeartbeat == null || ownerHeartbeat.isValid() == false);
    }

}
