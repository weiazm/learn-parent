/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.test.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @title RedisTemplateTest
 * @desc description
 * @author weihongyan
 * @date Aug 30, 2016
 * @version version
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-config.xml")
public class RedisTemplateTest {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() {
        String key = "whatsthisfuck?";
        BoundListOperations<String, String> bo = stringRedisTemplate.boundListOps(key);
        System.out.println(bo.size());
    }

    @Test
    public void putAndGet() {
        redisTemplate.opsForHash().put("user", "name", "张三");
        Object name = redisTemplate.opsForHash().get("user", "name");
        System.out.println(name);
        redisTemplate.opsForHash().delete("user", "name");
    }

    @Test
    public void redisCallBackTest() {
        String res = stringRedisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx("张三".getBytes(), 2L, "经纪人".getBytes());
                return new String(connection.get("张三".getBytes()));
            }
        });
        System.out.println(res);
    }

}
