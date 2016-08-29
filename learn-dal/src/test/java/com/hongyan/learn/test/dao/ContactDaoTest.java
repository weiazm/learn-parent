/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.test.dao;

import com.hongyan.learn.dal.dao.ContactDao;
import com.hongyan.learn.dal.po.Contact;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @title ContactDaoTest
 * @desc description
 * @author weihongyan
 * @date Aug 29, 2016
 * @version version
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-config.xml")
public class ContactDaoTest {
    @Autowired
    private ContactDao contactDao;

    @Test
    public void testGetContactByName() {
        String name = "张三";
        Contact contact = contactDao.getByName(name);
        Assert.assertNotNull(contact);
        Assert.assertEquals(contact.getName(), name);
        System.out.println(contact);
    }
}
