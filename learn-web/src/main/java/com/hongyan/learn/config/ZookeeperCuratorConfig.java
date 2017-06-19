/*
 * Baijiahulian.com Inc. Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by weihongyan on 9/14/16.
 */
@Configuration
@PropertySource(value = { "classpath:zookeeper.properties" })
public class ZookeeperCuratorConfig {

    @Bean
    public CuratorFramework curatorFramework(@Value("${zookeeper.servers}") String redisStr) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(redisStr, retryPolicy);
        return curatorFramework;
    }

}
