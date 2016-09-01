/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.test.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
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
    private RedisTemplate<String, Object> jedisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() {
        Assert.assertNotNull(jedisTemplate);
        Assert.assertNotNull(stringRedisTemplate);
        System.out.println(jedisTemplate.getClass().getName());
        System.out.println(stringRedisTemplate.getClass().getName());
    }

    @Test
    public void putAndGet() {
        jedisTemplate.opsForHash().put("user", "name", "张三");
        Object name = jedisTemplate.opsForHash().get("user", "name");
        System.out.println(name);
        jedisTemplate.opsForHash().delete("user", "name");
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
