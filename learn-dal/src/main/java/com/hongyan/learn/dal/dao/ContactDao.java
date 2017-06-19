/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.dal.dao;

import com.baijia.tianxiao.sqlbuilder.support.CommonDao;

import com.hongyan.learn.dal.po.Contact;

/**
 * @title ContactDao
 * @desc 1+1=2
 * @author hongyan
 * @date Aug 27, 2016
 * @version 1.0
 */
public interface ContactDao extends CommonDao<Contact> {
    public Contact getByName(String name);
}
