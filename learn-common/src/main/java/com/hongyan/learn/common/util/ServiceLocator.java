/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.hongyan.learn.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @title ServiceLocator
 * @desc TODO
 * @author taoyaping
 * @date 2015年3月15日
 * @version 1.0
 */
public class ServiceLocator implements ApplicationContextAware, InitializingBean {

    private static ApplicationContext context;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (null == context) {
            throw new RuntimeException("ServiceLocator init failed ...");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ServiceLocator.context = applicationContext;
    }

    public static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }
}
