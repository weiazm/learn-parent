/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.others;

import com.hongyan.learn.config.ApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Created by weihongyan on 9/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class PropertiesTest {
    //    @Value("#{properties.creater}")
    private String creater = "";

    @Test
    public void test() {
        Assert.notNull(creater);
        System.out.println(creater);
    }
}
