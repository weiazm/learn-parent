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

    private static final long serialVersionUID = 970850729790501922L;
    /**
     * 锁的拥有者
     */
    private String owner;
    /**
     * 上锁时间
     */
    private long time;
    /**
     * 心跳开始时间
     */
    private long heartbeatStartAt;

}