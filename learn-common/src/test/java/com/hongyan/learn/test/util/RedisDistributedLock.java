/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.test.util;

import com.hongyan.learn.common.springConfig.SpringRedisConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @title RedisDistributedLock
 * @desc thebestisyettocome
 * @author weihongyan
 * @date Sep 2, 2016
 * @version null
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringRedisConfig.class)
public class RedisDistributedLock {

    @Autowired
    private StringRedisTemplate tp;

    @Autowired
    private RedisConnection rc;

    @Test
    public void test() {
        org.junit.Assert.assertNotNull(tp);
        org.junit.Assert.assertNotNull(rc);
    }
}
