/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by weihongyan on 9/9/16.
 */
@Configuration
@PropertySource(value = {"classpath:redis.properties"})
public class SpringRedisConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(
            @Value(value = "${redis.host}") String hostName,
            @Value(value = "${redis.port}") Integer port,
            @Value(value = "${redis.pass}") String password,
            @Value(value = "${redis.timeout}") Integer timeout,
            @Value(value = "${redis.default.db}") Integer database,
            @Value(value = "${redis.usePool}") Boolean usePool
    ) {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(hostName);
        factory.setPort(port);
        factory.setPassword(password);
        factory.setDatabase(database);
        factory.setTimeout(timeout);
        factory.setUsePool(usePool);
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(60);//尼玛逼 默认值是8 真坑爹.
        config.setMaxIdle(10);
        config.setTestOnBorrow(true);
        factory.setPoolConfig(config);
        return factory;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }

    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory factory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
