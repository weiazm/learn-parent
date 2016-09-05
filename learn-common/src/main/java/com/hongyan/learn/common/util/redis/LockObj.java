package com.hongyan.learn.common.util.redis;

import java.io.Serializable;

import lombok.Data;

/**
 * 锁对象，保存锁信息
 * 
 * @title LockObj
 * @desc 区分同一个owner，不同时间的锁，锁的拥有者心跳开始时间（避免解锁前重启导致锁不能释放）
 * @author purerboy
 * @date 2015年9月10日
 * @version 1.0
 */
@Data
public class LockObj implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 970850729790501922L;
    private String owner;
    private long time;
    private long heartbeatStartAt;

    /**
     * 锁的拥有者
     * 
     * @return
     */
    public String getOwner() {
        return owner;
    }

    /**
     * 锁的拥有者
     * 
     * @param owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * 上锁时间
     * 
     * @return
     */
    public long getTime() {
        return time;
    }

    /**
     * 上锁时间
     * 
     * @param time
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * 心跳开始时间
     * 
     * @return
     */
    public long getHeartbeatStartAt() {
        return heartbeatStartAt;
    }

    /**
     * 心跳开始时间
     * 
     * @param heartbeatStartAt
     */
    public void setHeartbeatStartAt(long heartbeatStartAt) {
        this.heartbeatStartAt = heartbeatStartAt;
    }

}