/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.test.springConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @title SpringRedisConfig
 * @desc thebestisyettocome
 * @author weihongyan
 * @date Sep 2, 2016
 * @version null
 */
@Configuration
@PropertySource(value = { "classpath:redis.properties" })
public class SpringRedisConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(
        @Value(value = "${redis.host}")String hostName,
        @Value(value = "${redis.port}")Integer port,
        @Value(value = "${redis.pass}")String password,
        @Value(value = "${redis.timeout}")Integer timeout,
        @Value(value = "${redis.default.db}")Integer database,
        @Value(value = "${redis.usePool}")Boolean usePool
        ) {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(hostName);
        factory.setPort(port);
        factory.setPassword(password);
        factory.setDatabase(database);
        factory.setTimeout(timeout);
        factory.setUsePool(usePool);
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(50);//尼玛逼 默认值是8 真坑爹.
        factory.setPoolConfig(config);
        return factory;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }


//     RedisConnection绝不能搞成单例公用,卧槽太傻逼了.
//    @Bean
//    public RedisConnection redisConnection(JedisConnectionFactory factory) {
//        return factory.getConnection();
//    }
}
