/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.test.dao;

import com.hongyan.learn.config.DatabaseConfig;
import com.hongyan.learn.dal.dao.ContactDao;
import com.hongyan.learn.dal.mapper.ContactMapper;
import com.hongyan.learn.dal.po.Contact;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author weihongyan
 * @version version
 * @title ContactDaoTest
 * @desc description
 * @date Aug 29, 2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DatabaseConfig.class)
public class ContactDaoTest {
    @Autowired
    private ContactDao contactDao;

    @Autowired
    private ContactMapper contactMapper;

    @Test
    public void testGetContactByName() {
        String name = "张三";
        Contact contact = contactDao.getByName(name);
        Assert.assertNotNull(contact);
        Assert.assertEquals(contact.getName(), name);
        System.out.println(contact);
    }

    @Test
    public void testGetContactMapperByName() {
        String name = "张三";
        Contact contact = contactMapper.getContact(name);
        Assert.assertNotNull(contact);
        Assert.assertEquals(contact.getName(), name);
        System.out.println(contact);
    }
}
