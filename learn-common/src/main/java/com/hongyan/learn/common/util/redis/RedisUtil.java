package com.hongyan.learn.common.util.redis;


import com.google.common.primitives.Longs;
import com.hongyan.learn.common.util.BaseUtils;
import com.hongyan.learn.common.util.CollectionUtils;
import com.hongyan.learn.common.util.DoubleCheckSingleton;
import com.hongyan.learn.common.util.ServiceLocator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import redis.clients.jedis.Response;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * redis工具类，依赖{@link redis.clients.jedis.ShardedJedisPool ShardedJedisPool}和
 * {@link com.baijia.commons.lang.utils.ServiceLocator ServiceLocator}，配置好这两个bean后，可自动加载ShardedJedisPool实例，自动重试，避免闪断故障
 * 
 * @title RedisUtil
 * @desc
 * @author purerboy
 * @date 2015年9月15日
 * @version 1.0
 */
public class RedisUtil {

    public static interface RedisAction<T> {
        String getName();

        T doAction(ShardedJedis jedis);
    }

    /**
     * redis批量操作接口，K为参数类型，V为返回值类型
     * 
     * @title PipelineAction
     * @desc description
     * @author purerboy
     * @date 2015年12月15日
     * @version version
     */
    public static interface PipelineAction<K, V> {
        String getName();

        Response<V> doAction(ShardedJedisPipeline p, K key);
    }

    private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * redis操作执行成功返回值：{@value}
     */
    public static final long OK_RESULT = 1;

    /**
     * redis操作执行失败返回值：{@value}
     */
    public static final long NOT_OK_RESULT = 0;

    public static final int DEF_RETYR_TIMES = 10;
    public static final long DEF_WAIT_TIME = 100;

    private static RedisUtil ins;

    @Getter
    @Setter
    private ShardedJedisPool jedisPool;
    
    private int retryTimes = DEF_RETYR_TIMES;
    private long waitTime = DEF_WAIT_TIME;

    /**
     * @param jedisPool
     */
    public RedisUtil(ShardedJedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * @param jedisPool redis连接池
     * @param retryTimes 重试次数
     * @param waitTime 重试等待时间
     */
    public RedisUtil(ShardedJedisPool jedisPool, int retryTimes, long waitTime) {
        this.jedisPool = jedisPool;
        this.retryTimes = retryTimes;
        this.waitTime = waitTime;
    }

    /**
     * 获取单例，如果未配置，自动通过Locater生成
     * 
     * @return
     */
    public static RedisUtil getIns() {
        BaseUtils.setSingleton(new DoubleCheckSingleton<RedisUtil>() {

            @Override
            public RedisUtil create() {
                return new RedisUtil(ServiceLocator.getBean(ShardedJedisPool.class), DEF_RETYR_TIMES, DEF_WAIT_TIME);
            }

            @Override
            public RedisUtil getSingleton() {
                return ins;
            }

            @Override
            public void setSingleton(RedisUtil t) {
                ins = t;
            }
        }, RedisUtil.class);
        return ins;
    }

    /**
     * 执行一个redis操作
     * 
     * @param action
     * @return
     * @throws Exception
     */
    public <T> T doRedisAction(RedisAction<T> action) throws Exception {
        return doRedisAction(action, retryTimes, waitTime);
    }

    /**
     * 执行一个redis操作，会进行重试
     * 
     * @param action redis操作
     * @param retryTimes 重试次数
     * @param waitTime 重试等待时间
     * @return
     * @throws Exception
     */
    public <T> T doRedisAction(RedisAction<T> action, int retryTimes, long waitTime) throws Exception {
        int retryCount = 0;
        ShardedJedis jedis = null;
        while (true) {
            try {
                jedis = jedisPool.getResource();
                return action.doAction(jedis);
            } catch (JedisConnectionException e) {
                if (retryCount++ >= retryTimes) {
                    throw e;
                }
                log.error("conn error happened when do redis action[" + action.getName() + "]! retry " + retryCount
                    + " times!", e);
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e1) {
                }
            } finally {
                jedis.close();
            }
        }
    }

    /**
     * 如果redis中不存在key，则设置该key的值为value，设置成功返回{@value #OK_RESULT}
     * 
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    public Long setnx(final String key, final String value) throws Exception {
        return setnx(key, value, "setnx");
    }

    /**
     * 如果redis中不存在key，则设置该key的值为value，设置成功返回{@value #OK_RESULT}
     * 
     * @param key
     * @param value
     * @param name
     * @return
     * @throws Exception
     */
    public Long setnx(final String key, final String value, final String name) throws Exception {
        return doRedisAction(new RedisAction<Long>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Long doAction(ShardedJedis jedis) {
                return jedis.setnx(key, value);
            }
        });
    }

    /**
     * setnx值的同时设置过期时间
     * 
     * @param key
     * @param value
     * @param expireTime 单位秒
     * @return
     * @throws Exception
     */
    public Long setnx(final String key, final String value, final long expireTime) throws Exception {
        return setnx(key, value, expireTime, "setnx");
    }

    /**
     * setnx值的同时设置过期时间
     * 
     * @param key
     * @param value
     * @param expireTime 单位秒
     * @param name
     * @return
     * @throws Exception
     */
    public Long setnx(final String key, final String value, final long expireTime, final String name) throws Exception {
        return doRedisAction(new RedisAction<Long>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Long doAction(ShardedJedis jedis) {
                if ("OK".equalsIgnoreCase(jedis.set(key, value, "NX", "EX", expireTime))) {
                    return OK_RESULT;
                } else {
                    return NOT_OK_RESULT;
                }
            }
        });
    }

    /**
     * 为key设置过期时间
     * 
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    public Long expire(final String key, final int value) throws Exception {
        return expire(key, value, "expire");
    }

    /**
     * 为key设置过期时间
     * 
     * @param key
     * @param value
     * @param name
     * @return
     * @throws Exception
     */
    public Long expire(final String key, final int value, final String name) throws Exception {
        return doRedisAction(new RedisAction<Long>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Long doAction(ShardedJedis jedis) {
                return jedis.expire(key, value);
            }
        });
    }

    public String get(final String key) throws Exception {
        return get(key, "get");
    }

    public String get(final String key, final String name) throws Exception {
        return doRedisAction(new RedisAction<String>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String doAction(ShardedJedis jedis) {
                return jedis.get(key);
            }
        });
    }

    /**
     * 将key的值设置为value
     * 
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    public String set(final String key, final String value) throws Exception {
        return set(key, value, "set");
    }

    /**
     * 将key的值设置为value
     * 
     * @param key
     * @param value
     * @param name
     * @return
     * @throws Exception
     */
    public String set(final String key, final String value, final String name) throws Exception {
        return doRedisAction(new RedisAction<String>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String doAction(ShardedJedis jedis) {
                return jedis.set(key, value);
            }
        });
    }

    /**
     * 删除key
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public Long del(final String key) throws Exception {
        return del(key, "del");
    }

    /**
     * 删除key
     * 
     * @param key
     * @param name
     * @return
     * @throws Exception
     */
    public Long del(final String key, final String name) throws Exception {
        return doRedisAction(new RedisAction<Long>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Long doAction(ShardedJedis jedis) {
                return jedis.del(key);
            }
        });
    }

    /**
     * KEY是否存在
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public Boolean exists(final String key) throws Exception {
        return exists(key, "exists");
    }

    /**
     * KEY是否存在
     * 
     * @param key
     * @param name
     * @return
     * @throws Exception
     */
    public Boolean exists(final String key, final String name) throws Exception {
        return doRedisAction(new RedisAction<Boolean>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Boolean doAction(ShardedJedis jedis) {
                return jedis.exists(key);
            }
        });
    }

    private static final int batch_size = 1000;

    /**
     * 向set中add一组val
     * 
     * @param key
     * @param set
     * @throws Exception
     */
    public void saddLongSet(final String key, final Collection<Long> sets) throws Exception {
        saddLongSet(key, "saddLongSet", sets);
    }

    /**
     * 向set中add一组val
     * 
     * @param key
     * @param set
     * @throws Exception
     */
    public void saddLongSet(final String key, final String name, final Collection<Long> sets) throws Exception {
        doRedisAction(new RedisAction<Long>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Long doAction(ShardedJedis jedis) {
                int size = sets.size();
                size = batch_size > size ? size : batch_size;
                byte[][] temp = new byte[size][];
                int i = 0;
                for (List<Long> set : CollectionUtils.split(sets, size)) {
                    i = 0;
                    if (set.size() != size) {
                        size = set.size();
                        temp = new byte[size][];
                    }
                    for (Long num : set) {
                        temp[i] = Longs.toByteArray(num);
                        i++;
                    }
                    jedis.sadd(key.getBytes(), temp);
                }
                return 1L;
            }
        });
    }

    /**
     * 向set中add一个val
     * 
     * @param key
     * @param val
     * @return
     * @throws Exception
     */
    public Long sadd(final String key, final long val) throws Exception {
        return sadd(key, val, "sadd");
    }

    /**
     * 向set中add一个val
     * 
     * @param key
     * @param val
     * @param name
     * @return
     * @throws Exception
     */
    public Long sadd(final String key, final long val, final String name) throws Exception {
        return doRedisAction(new RedisAction<Long>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Long doAction(ShardedJedis jedis) {
                return jedis.sadd(key.getBytes(), Longs.toByteArray(val));
            }
        });
    }

    /**
     * 向set中add一组val
     * 
     * @param key
     * @param set
     * @throws Exception
     */
    public void saddStrSet(final String key, final Collection<String> set) throws Exception {
        saddStrSet(key, "saddStrSet", set);
    }

    /**
     * 向set中add一组val
     * 
     * @param key
     * @param set
     * @throws Exception
     */
    public void saddStrSet(final String key, final String name, final Collection<String> sets) throws Exception {
        doRedisAction(new RedisAction<Long>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Long doAction(ShardedJedis jedis) {
                int size = sets.size();
                size = batch_size > size ? size : batch_size;
                String[] temp = new String[size];
                int i = 0;
                for (List<String> set : CollectionUtils.split(sets, size)) {
                    i = 0;
                    if (set.size() != size) {
                        size = set.size();
                        temp = new String[size];
                    }
                    for (String s : set) {
                        temp[i] = s;
                        i++;
                    }
                    jedis.sadd(key, temp);
                }
                return 1L;
            }
        });
    }

    /**
     * 向set中add一个val
     * 
     * @param key
     * @param val
     * @return
     * @throws Exception
     */
    public Long sadd(final String key, final String val) throws Exception {
        return sadd(key, val, "sadd");
    }

    /**
     * 向set中add一个val
     * 
     * @param key
     * @param val
     * @param name
     * @return
     * @throws Exception
     */
    public Long sadd(final String key, final String val, final String name) throws Exception {
        return doRedisAction(new RedisAction<Long>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Long doAction(ShardedJedis jedis) {
                return jedis.sadd(key, val);
            }
        });
    }

    /**
     * 查询set的内容
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public Set<String> smembers(final String key) throws Exception {
        return smembers(key, "smembers");
    }

    /**
     * 查询set的内容
     * 
     * @param key
     * @param name
     * @return
     * @throws Exception
     */
    public Set<String> smembers(final String key, final String name) throws Exception {
        return doRedisAction(new RedisAction<Set<String>>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Set<String> doAction(ShardedJedis jedis) {
                return jedis.smembers(key);
            }
        });
    }

    /**
     * 查询set的size
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public Long scard(final String key) throws Exception {
        return scard(key, "scard");
    }

    /**
     * 查询set的size
     * 
     * @param key
     * @param name
     * @return
     * @throws Exception
     */
    public Long scard(final String key, final String name) throws Exception {
        return doRedisAction(new RedisAction<Long>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Long doAction(ShardedJedis jedis) {
                return jedis.scard(key);
            }
        });
    }

    /**
     * 通用批量操作方法
     * 
     * @param keys 参数
     * @param action 操作
     * @return
     * @throws Exception
     */
    public <K, V> Map<K, V> pipeline(final Collection<K> keys, final PipelineAction<K, V> action) throws Exception {
        if (keys == null || keys.isEmpty()) {
            return Collections.emptyMap();
        }
        return doRedisAction(new RedisAction<Map<K, V>>() {

            @Override
            public String getName() {
                return action.getName();
            }

            @Override
            public Map<K, V> doAction(ShardedJedis jedis) {
                ShardedJedisPipeline p;
                int size = keys.size();
                Map<K, V> result = new HashMap<>(size);
                size = batch_size > size ? size : batch_size;
                Map<K, Response<V>> responses = new HashMap<>(size);
                for (List<K> list : CollectionUtils.split(keys, size)) {
                    p = jedis.pipelined();
                    for (K key : list) {
                        responses.put(key, action.doAction(p, key));
                    }
                    p.sync();
                    for (K k : responses.keySet()) {
                        result.put(k, responses.get(k).get());
                    }
                    responses.clear();
                }
                return result;
            }
        });
    }

    /**
     * 批量操作scard
     * 
     * @param keys
     * @return
     * @throws Exception
     */
    public Map<String, Long> scard(final Collection<String> keys) throws Exception {
        return scard(keys, "scard by pipeline");
    }

    /**
     * 批量操作scard
     * 
     * @param keys
     * @param name
     * @return
     * @throws Exception
     */
    public Map<String, Long> scard(final Collection<String> keys, final String name) throws Exception {
        return pipeline(keys, new PipelineAction<String, Long>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Response<Long> doAction(ShardedJedisPipeline p, String key) {
                return p.scard(key);
            }
        });
    }

    /**
     * 批量操作smembers
     * 
     * @param keys
     * @return
     * @throws Exception
     */
    public Map<String, Set<String>> smembers(final Collection<String> keys) throws Exception {
        return smembers(keys, "smembers by pipeline");
    }

    /**
     * 批量操作smembers
     * 
     * @param keys
     * @param name
     * @return
     * @throws Exception
     */
    public Map<String, Set<String>> smembers(final Collection<String> keys, final String name) throws Exception {
        return pipeline(keys, new PipelineAction<String, Set<String>>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Response<Set<String>> doAction(ShardedJedisPipeline p, String key) {

                return p.smembers(key);
            }
        });
    }

    /**
     * 批量删除
     * 
     * @param keys
     * @return
     * @throws Exception
     */
    public Map<String, Long> del(final Collection<String> keys) throws Exception {
        return del(keys, "del by pipeline");
    }

    /**
     * 批量删除
     * 
     * @param keys
     * @param name
     * @return
     * @throws Exception
     */
    public Map<String, Long> del(final Collection<String> keys, final String name) throws Exception {
        return pipeline(keys, new PipelineAction<String, Long>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Response<Long> doAction(ShardedJedisPipeline p, String key) {
                return p.del(key);
            }
        });
    }

    /**
     * 批量设置过时时间
     * 
     * @param keys
     * @return
     * @throws Exception
     */
    public Map<String, Long> expire(final Collection<String> keys, final int value) throws Exception {
        return expire(keys, value, "expire by pipeline");
    }

    /**
     * 批量设置过时时间
     * 
     * @param keys
     * @param name
     * @return
     * @throws Exception
     */
    public Map<String, Long> expire(final Collection<String> keys, final int value, final String name) throws Exception {
        return pipeline(keys, new PipelineAction<String, Long>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Response<Long> doAction(ShardedJedisPipeline p, String key) {
                return p.expire(key, value);
            }
        });
    }

    /**
     * 批量saddLong
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public void saddLong(final Map<String, ? extends Collection<Long>> map) throws Exception {
        saddLong(map, "saddLong by pipeline");
    }

    /**
     * 批量saddLong
     * 
     * @param map
     * @param name
     * @return
     * @throws Exception
     */
    public void saddLong(final Map<String, ? extends Collection<Long>> map, final String name) throws Exception {
        if (map == null || map.isEmpty()) {
            return;
        }
        doRedisAction(new RedisAction<Void>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Void doAction(ShardedJedis jedis) {
                ShardedJedisPipeline p = null;
                int count = 0;
                Collection<Long> sets;
                int size;
                byte[][] temp;
                int i;
                for (String key : map.keySet()) {
                    sets = map.get(key);
                    size = sets.size();
                    if (size <= 0) {
                        continue;
                    }
                    size = batch_size > size ? size : batch_size;
                    temp = new byte[size][];
                    for (List<Long> set : CollectionUtils.split(sets, size)) {
                        if (count == 0) {
                            p = jedis.pipelined();
                        }
                        i = 0;
                        if (set.size() != size) {
                            size = set.size();
                            temp = new byte[size][];
                        }
                        for (Long num : set) {
                            temp[i] = Longs.toByteArray(num);
                            i++;
                        }
                        p.sadd(key.getBytes(), temp);
                        count++;
                        if (count >= batch_size) {
                            p.sync();
                            count = 0;
                        }
                    }
                }
                if (count > 0) {
                    p.sync();
                }
                return null;
            }

        });
    }

    /**
     * 批量saddStr
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public void saddStr(final Map<String, ? extends Collection<String>> map) throws Exception {
        saddStr(map, "saddLong by pipeline");
    }

    /**
     * 批量saddStr
     * 
     * @param map
     * @param name
     * @return
     * @throws Exception
     */
    public void saddStr(final Map<String, ? extends Collection<String>> map, final String name) throws Exception {
        if (map == null || map.isEmpty()) {
            return;
        }
        doRedisAction(new RedisAction<Void>() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Void doAction(ShardedJedis jedis) {
                ShardedJedisPipeline p = null;
                int count = 0;
                Collection<String> sets;
                int size;
                String[] temp;
                int i;
                for (String key : map.keySet()) {
                    sets = map.get(key);
                    size = sets.size();
                    if (size <= 0) {
                        continue;
                    }
                    size = batch_size > size ? size : batch_size;
                    temp = new String[size];
                    for (List<String> set : CollectionUtils.split(sets, size)) {
                        if (count == 0) {
                            p = jedis.pipelined();
                        }
                        i = 0;
                        if (set.size() != size) {
                            size = set.size();
                            temp = new String[size];
                        }
                        for (String s : set) {
                            temp[i] = s;
                            i++;
                        }
                        p.sadd(key, temp);
                        count++;
                        if (count >= batch_size) {
                            p.sync();
                            count = 0;
                        }
                    }
                }
                if (count > 0) {
                    p.sync();
                }
                return null;
            }

        });
    }

    /**
     * 释放连接池
     */
    public void destroy() {
        jedisPool.destroy();
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:ctx-redis.xml");
        ServiceLocator locator = new ServiceLocator();
        locator.setApplicationContext(context);

        RedisUtil redisUtil = RedisUtil.getIns();

        testRetry(redisUtil);

        testSetExpireExpire(redisUtil);

        testSetExpireSet(redisUtil);

    }

    private static void testRetry(RedisUtil redisUtil) throws Exception {
        System.out.println("==============================================");
        log.info("test retry");
        final String key = "ljx-20150909-test-key";
        redisUtil.doRedisAction(new RedisAction<String>() {

            @Override
            public String getName() {
                return "retry get";
            }

            @Override
            public String doAction(ShardedJedis jedis) {
                return jedis.get(key);
            }
        }, 3, 200);
    }

    private static void testSetExpireExpire(RedisUtil redisUtil) throws Exception {
        final String key = "ljx-20150909-test-key";
        final String value = "ljx-20150909-test-value";
        final int expireTime = 2;

        System.out.println("==============================================");
        log.info("test set expire expire");
        redisUtil.setnx(key, value);
        redisUtil.expire(key, expireTime);
        long start = System.currentTimeMillis();
        Thread.sleep(1000L);
        log.info("is expire:" + (redisUtil.get(key) == null));
        redisUtil.expire(key, expireTime);
        while (redisUtil.get(key) != null) {
            Thread.sleep(100L);
        }
        long end = System.currentTimeMillis();
        log.info("expire after:" + (end - start));
    }

    private static void testSetExpireSet(RedisUtil redisUtil) throws Exception {
        final String key = "ljx-20150909-test-key";
        final String value = "ljx-20150909-test-value";
        final int expireTime = 2;

        System.out.println("==============================================");
        log.info("test set expire set");
        redisUtil.setnx(key, value);
        redisUtil.expire(key, expireTime);
        long start = System.currentTimeMillis();
        Thread.sleep(1000L);
        log.info("is expire:" + (redisUtil.get(key) == null));
        redisUtil.set(key, value);
        int count = 0;
        while (redisUtil.get(key) != null && count++ < 100) {
            Thread.sleep(100L);
        }
        long end = System.currentTimeMillis();
        if (redisUtil.get(key) != null) {
            redisUtil.del(key);
            log.info("unexpire,del it!");
        } else {
            log.info("expire after:" + (end - start));
        }
    }
}
