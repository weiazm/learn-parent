/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by weihongyan on 9/9/16.
 */
@Configuration
@ImportResource(value = "classpath:ctx-hag-all.xml")
@Import(value = {SpringRedisConfig.class, DatabaseConfig.class})
public class ApplicationConfig {

    @Bean(name = "properties")
    public PropertiesFactoryBean propertiesFactoryBean() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        ClassPathResource[] resources = {
                new ClassPathResource("hag.properties")//here not "classpath:hag.properties";
        };
        propertiesFactoryBean.setLocations(resources);
        return propertiesFactoryBean;
    }

}
