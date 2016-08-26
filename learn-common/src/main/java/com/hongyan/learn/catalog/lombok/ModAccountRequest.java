/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.lombok;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @title dsf
 * @desc description
 * @author hongyan
 * @date 2016年8月2日
 * @version version
 */
@Data
@EqualsAndHashCode(callSuper = false)
//callSuper=false意味着在计算equals或者hashcode时不考虑父类的值
//callSuper=true 在计算equals或者hashcode时考虑其父类的值
public class ModAccountRequest extends AddAccountRequest {
    // txcascadeaccount.id
    private Integer credentialId;
    private String code;
    private Integer targetCampusNumber;
    private Integer status;
    private String campusName;

    @Override
    public boolean validRequest() {
        return true;
    }
    public static void main(String[] args) {
        AddAccountRequest a = new AddAccountRequest();
        AddAccountRequest b = new AddAccountRequest();
        b.mobile = "f";
        a.mobile = "f";
        ModAccountRequest c = new ModAccountRequest();
        ModAccountRequest d = new ModAccountRequest();
        System.out.println(a);
        System.out.println(b);
        System.out.println(a.equals(b));
        c.mobile = "f";
        d.mobile = "g";
        
        c.setCode("g");
        d.setCode("g");
        
        System.out.println(c);
        System.out.println(d);
        System.out.println(c.equals(d));
    }
}

