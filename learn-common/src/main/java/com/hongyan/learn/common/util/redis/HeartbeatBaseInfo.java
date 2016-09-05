package com.hongyan.learn.common.util.redis;

import java.io.Serializable;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 进程心跳信息
 * 
 * @title HeartbeatBaseInfo
 * @desc TODO
 * @author purerboy
 * @date 2015年9月15日
 * @version 1.0
 */
public class HeartbeatBaseInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5228727793705760815L;

    private static final int THRESHOLD = 10;

    protected static Gson gson = new Gson();

    private String procName;
    private long startAt;
    private long lastBeatTime;
    private long interval;
    private long count;

    /**
     * 进程名
     * 
     * @return
     */
    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    /**
     * 心跳开始时间
     * 
     * @return
     */
    public long getStartAt() {
        return startAt;
    }

    public void setStartAt(long startAt) {
        this.startAt = startAt;
    }

    /**
     * 最后一次心跳时间
     * 
     * @return
     */
    public long getLastBeatTime() {
        return lastBeatTime;
    }

    public void setLastBeatTime(long lastBeatTime) {
        this.lastBeatTime = lastBeatTime;
    }

    /**
     * 心跳周期，单位毫秒
     * 
     * @return
     */
    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    /**
     * 心跳次数
     * 
     * @return
     */
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    /**
     * 生成json
     * 
     * @return
     */
    public String toJson() {
        return gson.toJson(this);
    }

    /**
     * 是否合法，如果返回FALSE，认为进程已挂
     * 
     * @return
     */
    public boolean isValid() {
        return (System.currentTimeMillis() - lastBeatTime) / interval < THRESHOLD;
    }

    @Override
    public String toString() {
        return toJson();
    }

    public static final Type type = new TypeToken<HeartbeatBaseInfo>() {
    }.getType();

    public static HeartbeatBaseInfo fromJson(String json) {
        return gson.fromJson(json, type);
    }
}
