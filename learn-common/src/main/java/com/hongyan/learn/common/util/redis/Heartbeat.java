package com.hongyan.learn.common.util.redis;

import com.hongyan.learn.common.util.BaseUtils;
import com.hongyan.learn.common.util.DoubleCheckSingleton;
import com.hongyan.learn.common.util.ServiceLocator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 进程心跳，依赖Redis，使用RedisUtil，如果一个同名心跳已经存在，则会退出当前进程，默认启动一个线程，默认心跳名称组成规则：用户名@主机名:启动目录
 * 
 * @title Heartbeat
 * @desc TODO
 * @author purerboy
 * @date 2015年9月15日
 * @version 1.0
 */
public class Heartbeat extends Thread {

    private static final Logger log = LoggerFactory.getLogger(Heartbeat.class);
    public static int DEF_EXPIRE_TIME = 5 * 60;
    private static Heartbeat myHeartbeat;
    private RedisUtil redisUtil;

    private String procName;
    private long interval = 10 * 1000;
    private long startAt = System.currentTimeMillis();
    private int expireTime = DEF_EXPIRE_TIME;// 心跳过期时间，单位:秒，<=0时，无过期时间

    private HeartbeatBaseInfo heartbeatInfo;
    private volatile boolean run = false;

    public Heartbeat() {
        this(RedisUtil.getIns(), DEF_EXPIRE_TIME);
    }

    /**
     * 
     * @param redisUtil redis连接池
     * @param expireTime 心跳过期时间，单位:秒，<=0时，无过期时间
     */
    public Heartbeat(RedisUtil redisUtil, int expireTime) {
        this(genDefProcName(), redisUtil, expireTime);
    }

    /**
     * @param procName 进程名
     * @param redisUtil redis连接池
     * @param expireTime 心跳过期时间，单位:秒，<=0时，无过期时间
     */
    public Heartbeat(String procName, RedisUtil redisUtil, int expireTime) {
        super();
        this.redisUtil = redisUtil;
        this.procName = procName;
        this.expireTime = expireTime;
        this.setDaemon(true);
    }

    private static String genDefProcName() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.name"));
        try {
            InetAddress ia = InetAddress.getLocalHost();
            sb.append("@").append(ia.getHostName());
        } catch (UnknownHostException e) {
        }
        sb.append(":").append(System.getProperty("user.dir"));
        return sb.toString();
    }

    /**
     * 读取指定进程名的心跳信息
     * 
     * @param procName
     * @param redisUtil
     * @return
     */
    public static HeartbeatBaseInfo readHeartbeatInfo(String procName, RedisUtil redisUtil) {
        String json = null;
        try {
            json = redisUtil.get(procName);
        } catch (Exception e) {
            log.error("read heartbeat error!", e);
        }
        return HeartbeatBaseInfo.fromJson(json);
    }

    /**
     * 写进程心跳
     * 
     * @param obj
     * @param expireTime
     * @param redisUtil
     * @throws Exception
     */
    protected static void writeHeartbeatInfo(HeartbeatBaseInfo obj, int expireTime, RedisUtil redisUtil)
        throws Exception {
        redisUtil.set(obj.getProcName(), obj.toJson());
        redisUtil.expire(obj.getProcName(), expireTime);
    }

    /**
     * 获取当前进程心跳实例，先通过Locater获取配置的
     * 
     * @return
     */
    public static Heartbeat getMyHeartbeat() {
        BaseUtils.setSingleton(new DoubleCheckSingleton<Heartbeat>() {

            @Override
            public Heartbeat create() {
                Heartbeat heartbeat = null;
                try {
                    heartbeat = ServiceLocator.getBean(Heartbeat.class);
                } catch (Exception e) {
                    log.info("undefine heartbeat! new a default one!", e);
                    heartbeat = new Heartbeat(RedisUtil.getIns(), DEF_EXPIRE_TIME);
                }
                heartbeat.init();
                return heartbeat;
            }

            @Override
            public Heartbeat getSingleton() {
                return myHeartbeat;
            }

            @Override
            public void setSingleton(Heartbeat t) {
                myHeartbeat = t;
            }
        }, Heartbeat.class);
        return myHeartbeat;
    }

    /**
     * 进程名
     * 
     * @return
     */
    public String getProcName() {
        return procName;
    }

    /**
     * 心跳周期
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
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        if (run) {
            run = false;
        }
    }

    protected HeartbeatBaseInfo genHeartbeatInfo() {
        return new HeartbeatBaseInfo();
    }

    @Override
    public void run() {
        run = true;
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e) {
        }
        while (run) {
            try {
                beat();
                Thread.sleep(interval);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 初始化心跳线程，检查是否存在同名存活心跳，如果存在，退出当前进程，否则，初始化心跳并启动心跳线程
     */
    public void init() {
        heartbeatInfo = readHeartbeatInfo();
        if (heartbeatInfo == null) {
            heartbeatInfo = genHeartbeatInfo();
        } else {
            long lastBeatTime = heartbeatInfo.getLastBeatTime();
            try {
                Thread.sleep(heartbeatInfo.getInterval() + 1000);
            } catch (InterruptedException e) {
            }
            heartbeatInfo = readHeartbeatInfo();
            if (heartbeatInfo != null) {
                if (heartbeatInfo.getLastBeatTime() != lastBeatTime) {
                    log.error("another proc[{}] is running, exiting...", getProcName());
                    System.exit(1);
                }
            }
        }
        heartbeatInfo.setProcName(getProcName());
        heartbeatInfo.setStartAt(startAt);
        heartbeatInfo.setInterval(interval);
        heartbeatInfo.setCount(0);
        try {
            beat();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.start();
    }

    private void beat() throws Exception {
        heartbeatInfo.setCount(heartbeatInfo.getCount() + 1);
        heartbeatInfo.setLastBeatTime(System.currentTimeMillis());
        writeHeartbeatInfo(heartbeatInfo, expireTime, redisUtil);
    }

    public HeartbeatBaseInfo getHeartbeatInfo() {
        return heartbeatInfo;
    }

    /**
     * 读取当前进程心跳信息
     * 
     * @return
     */
    public HeartbeatBaseInfo readHeartbeatInfo() {
        return readHeartbeatInfo(getProcName(), redisUtil);
    }

}
