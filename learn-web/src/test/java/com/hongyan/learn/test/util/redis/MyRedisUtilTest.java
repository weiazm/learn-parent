/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.util.redis;

import com.hongyan.learn.common.util.myRedis.MyRedisUtil;
import com.hongyan.learn.config.SpringRedisConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by weihongyan on 9/20/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringRedisConfig.class)
public class MyRedisUtilTest {

    private static final String mapKey = "weihongyan_map";
    private static final String listKey = "weihongyan_list";

    @Autowired
    private RedisConnectionFactory factory;

    @Test
    public void test(){
        RedisConnection connection = factory.getConnection();
        Long result = MyRedisUtil.rightAddList(connection,listKey,"redis作业");
        System.out.println(result);
    }

    @Test
    public void test2(){
        RedisConnection connection = factory.getConnection();
        Object o = MyRedisUtil.leftGetList(connection,listKey,String.class);
        System.out.println(o);
    }

    @Test
    public void test3(){
        RedisConnection connection = factory.getConnection();
        Object o = MyRedisUtil.putMap(connection,mapKey,"field","hahaha");
        System.out.println(o);
    }

    @Test
    public void test4(){
        RedisConnection connection = factory.getConnection();
        Object o = MyRedisUtil.getMap(connection,mapKey,"field",String.class);
        System.out.println(o);
    }

    @Test
    public void transaction(){
        RedisConnection connection = factory.getConnection();
        connection.watch(MyRedisUtil.serialize(mapKey),MyRedisUtil.serialize(listKey));
        connection.multi();
        System.out.println(MyRedisUtil.rightAddList(connection,listKey,"redis作业2"));
        System.out.println(MyRedisUtil.putMap(connection,mapKey,"field","hahaha2"));
        System.out.println(connection.exec());
    }

}
