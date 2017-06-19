/*
 * Baijiahulian.com Inc. Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.dal.mapper;

import com.hongyan.learn.dal.po.Contact;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by weihongyan on 9/9/16.
 */
public interface ContactMapper {
    @Select("SELECT * FROM test.contact WHERE name = #{name}")
    Contact getContact(@Param("name") String contactName);
}
