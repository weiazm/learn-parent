/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.test.service;

import com.hongyan.learn.sal.dto.ContactDto;
import com.hongyan.learn.sal.service.ContactService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @title ContactServiceTest
 * @desc description
 * @author weihongyan
 * @date Aug 29, 2016
 * @version version
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-config.xml")
public class ContactServiceTest {
    @Autowired
    private ContactService contactService;

    @Test
    public void contactServiceTest() {
        String name = "张三";
        ContactDto contact = contactService.getContactDtoByName(name);
        Assert.assertNotNull(contact);
        Assert.assertEquals(contact.getName(), name);
        System.out.println(contact);
    }
}
