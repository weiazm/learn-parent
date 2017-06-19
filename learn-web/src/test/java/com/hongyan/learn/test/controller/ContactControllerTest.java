/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.test.controller;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @title ContactControllerTest
 * @desc description
 * @author weihongyan
 * @date Aug 29, 2016
 * @version version
 */
public class ContactControllerTest extends SuperControllerTest {

    @Autowired
    private ApplicationContext context;

    // @Value(value = "#{systemProperties['redis.host']}")
    @Value(value = "${redis.host}")
    private String name;

    @Value(value = "#{jedisConnectionFactory.password}")
    private String password;

    @Value(value = "#{stringRedisTemplate}") // 真他妈神奇
    private StringRedisTemplate stringRedisTemplate;

    @Test
    @Ignore
    @Override
    public void test() {
        this.pathPrintTest("/contact/getByName.json?contactName=张三");
    }

    @Test
    public void test2() {
        // context = new ClassPathXmlApplicationContext("application-config.xml");
        JedisConnectionFactory redisTemplate = context.getBean(JedisConnectionFactory.class);
        System.out.println(redisTemplate.getPassword());
        System.out.println(name);
        System.out.println(password);
        Assert.assertNotNull(stringRedisTemplate);
    }
}
