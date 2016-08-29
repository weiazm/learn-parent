/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.test.controller;

import org.junit.Before;
import org.junit.Test;
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
 * @title ContactControllerTest
 * @desc description
 * @author weihongyan
 * @date Aug 29, 2016
 * @version version
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:application-config.xml", "classpath:mvc-config.xml" })
public class ContactControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Test
    public void contactControllerTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contact/getByName.json?contactName=张三"))
            .andDo(MockMvcResultHandlers.print());
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
