/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.test.controller;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
    private static ApplicationContext context;

    @Override
    public void test() {
        this.pathPrintTest("/contact/getByName.json?contactName=张三");
    }

    @Test
    public void test2() {
        context = new ClassPathXmlApplicationContext("application-config.xml");
        StringRedisTemplate redisTemplate = (StringRedisTemplate) context.getBean("redisTemplate");
        System.out.println(((JedisConnectionFactory) (redisTemplate.getConnectionFactory())).getPassword());
    }
}
