/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by weihongyan on 9/9/16.
 */
@Configuration
@EnableWebMvc
@ComponentScan(value = {"com.hongyan.learn.web.controller"})
public class MvcConfig {

//    @Bean
//    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(){
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(Lists.newArrayList(new MediaType("application/json;charset=UTF-8")));
//        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
//        adapter.setMessageConverters(Lists.newArrayList(converter));
//        return adapter;
//    }

//    @Bean
//    public ViewResolver viewResolver(ServletContext servletContext) throws Exception {
//        ServletLoader servletLoader = new ServletLoader(servletContext);
//        PebbleEngine pebbleEngine = PebbleEngineFactory.instance(servletLoader, new SpringExtension());
//
//        PebbleViewResolver viewResolver = new PebbleViewResolver();
//        viewResolver.setViewClass(PebbleJsonView.class);
//        viewResolver.setPrefix("/views");
//        viewResolver.setSuffix(".html");
//        viewResolver.setPebbleEngine(pebbleEngine);
//        return viewResolver;
//    }

//    @Bean(name = "servletContext")
//    public ServletContextFactory servletContextFactory(){
//        return new ServletContextFactory();
//    }
}
