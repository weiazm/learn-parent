/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.config;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ServletLoader;
import com.mitchellbosecke.pebble.spring.PebbleViewResolver;
import com.mitchellbosecke.pebble.spring.extension.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletContext;

/**
 * Created by weihongyan on 9/9/16.
 */
@Configuration
@EnableWebMvc
@ComponentScan(value = {"com.hongyan.learn.web.controller"})
public class MvcConfig extends WebMvcConfigurerAdapter {

//    @Bean
//    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(){
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(Lists.newArrayList(new MediaType("application/json;charset=UTF-8")));
//        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
//        adapter.setMessageConverters(Lists.newArrayList(converter));
//        return adapter;
//    }

    @Autowired
    private ServletContext servletContext;

    @Bean
    public SpringExtension springExtension() {
        return new SpringExtension();
    }

    @Bean
    public ViewResolver viewResolver() {
        PebbleViewResolver viewResolver = new PebbleViewResolver();
        viewResolver.setPrefix("/views");
        viewResolver.setSuffix(".html");
        viewResolver.setPebbleEngine(new PebbleEngine.Builder()
                .loader(new ServletLoader(servletContext))
                .extension(springExtension())
                .build());
        return viewResolver;
    }

//    @Bean//设置文件上传处理器
//    public MultipartResolver multipartResolver(){
//        return new StandardServletMultipartResolver();
//    }

    @Override//配置静态资源的处理
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
