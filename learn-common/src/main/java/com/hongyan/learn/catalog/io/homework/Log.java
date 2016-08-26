/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.io.homework;

import lombok.Data;

/**
 * 找出登录次数最多的用户，并记录下登录次数
找出某个用户所有的登录记录

 * 
 * @title Log
 * @desc description
 * @author hongyan
 * @date 2016年8月5日
 * @version 1.0
 */
@Data
class Log {
    String userName;
    String loginDate;
    String ip;
}
