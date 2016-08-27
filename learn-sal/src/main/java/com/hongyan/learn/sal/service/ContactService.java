/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.sal.service;

import com.hongyan.learn.sal.dto.ContactDto;

/**
 * @title ContactService
 * @desc 1+1=2
 * @author hongyan
 * @date Aug 27, 2016
 * @version 1.0
 */
public interface ContactService {

    public ContactDto getContactDtoByName(String name);

}
