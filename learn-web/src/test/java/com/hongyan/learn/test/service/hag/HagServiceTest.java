/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.service.hag;

import com.baijia.yunying.hag.service.HagService;
import com.hongyan.learn.config.ApplicationConfig;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Created by weihongyan on 9/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class HagServiceTest {

    @Autowired
    private HagService hagService;

    @Test
    @SneakyThrows
    public void test() {
        Assert.notNull(hagService);
        System.out.println(hagService.getEntityKeyOfResource("train_resource_1"));//根据资源名获取名单
    }
}
