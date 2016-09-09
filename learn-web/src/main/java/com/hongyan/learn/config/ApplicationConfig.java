/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by weihongyan on 9/9/16.
 */
@Configuration
@Import(value = {DatabaseConfig.class, SpringRedisConfig.class})
public class ApplicationConfig {
}
