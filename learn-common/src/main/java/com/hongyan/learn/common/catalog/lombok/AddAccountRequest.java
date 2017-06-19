/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.common.catalog.lombok;

import java.util.List;

import lombok.Data;

/**
 * @title sdf
 * @desc description
 * @author hongyan
 * @date 2016年8月2日
 * @version version
 */
@Data
public class AddAccountRequest {
    protected List<Integer> auth;
    protected Integer campusNumber;
    protected String mobile;
    protected String name;
    protected String title;
    protected Integer type;

    public boolean validRequest() {
        // TODO Auto-generated method stub
        return false;
    }
}