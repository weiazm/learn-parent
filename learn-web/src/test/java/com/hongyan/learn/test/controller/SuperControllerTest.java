/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.test.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @title SuperControllerTest
 * @desc 1+1=2
 * @author weihongyan
 * @date Aug 31, 2016
 * @version null
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:application-config.xml", "classpath:mvc-config.xml" })
public abstract class SuperControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    
    public abstract void test();

    public final void pathPrintTest(String path) {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(path)).andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Before
    public final void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
