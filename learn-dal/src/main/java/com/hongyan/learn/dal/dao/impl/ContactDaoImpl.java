/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.dal.dao.impl;

import com.baijia.tianxiao.sqlbuilder.SingleSqlBuilder;
import com.baijia.tianxiao.sqlbuilder.support.JdbcTemplateDaoSupport;

import com.hongyan.learn.dal.dao.ContactDao;
import com.hongyan.learn.dal.po.Contact;

import org.springframework.stereotype.Repository;

import lombok.NonNull;

/**
 * @title ContactDaoImpl
 * @desc 1+1=2
 * @author hongyan
 * @date Aug 27, 2016
 * @version 1.0
 */
@Repository
public class ContactDaoImpl extends JdbcTemplateDaoSupport<Contact> implements ContactDao {

    public ContactDaoImpl() {
        super(Contact.class);
    }

    @Override
    public Contact getByName(@NonNull String name) {
        SingleSqlBuilder<Contact> builder = createSqlBuilder();
        builder.eq("name", name);
        return uniqueResult(builder);
    }
}
