package com.hongyan.learn.common.util.redis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;

import lombok.Data;

/**
 * 进程心跳信息
 * 
 * @title HeartbeatBaseInfo
 * @desc TODO
 * @author purerboy
 * @date 2015年9月15日
 * @version 1.0
 */
@Data
public class HeartbeatBaseInfo implements Serializable {

    private static final long serialVersionUID = -5228727793705760815L;

    private static final int THRESHOLD = 10;

    protected static Gson gson = new Gson();

    /**
     * 进程名
     */
    private String procName;
    /**
     * 心跳开始时间
     */
    private long startAt;
    /**
     * 最后一次心跳时间
     */
    private long lastBeatTime;
    /**
     * 心跳周期,单位毫秒
     */
    private long interval;
    /**
     * 心跳次数
     */
    private long count;

    /**
     * 生成json
     * @return
     */
    public String toJson() {
        return gson.toJson(this);
    }

    /**
     * 是否合法，如果返回FALSE，认为进程已挂
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
