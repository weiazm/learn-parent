/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.sal.service.impl;

import com.hongyan.learn.dal.dao.ContactDao;
import com.hongyan.learn.dal.po.Contact;
import com.hongyan.learn.sal.dto.ContactDto;
import com.hongyan.learn.sal.service.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @title ContactServiceImpl
 * @desc 1+1=2
 * @author hongyan
 * @date Aug 27, 2016
 * @version 1.0
 */
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDao contactDao;

    @Override
    public ContactDto getContactDtoByName(String name) {
        Contact contact = contactDao.getByName(name);
        if (null == contact) {
            return null;
        }
        ContactDto result = new ContactDto();
        result.setId(contact.getId());
        result.setName(contact.getName());
        result.setMobile(contact.getMobile());
        return result;
    }

}
