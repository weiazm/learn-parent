/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.test.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    RedisTemplate<String, String> jedisTemplate;

    @Test
    public void putAndGet() {
        jedisTemplate.opsForHash().put("user", "name", "张三");
        Object name = jedisTemplate.opsForHash().get("user", "name");
        System.out.println(name);
        jedisTemplate.opsForHash().delete("user", "name");
    }

}
