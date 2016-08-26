/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @title MyPattern
 * @desc description
 * @author hongyan
 * @date 2016年8月3日
 * @version version
 */
public class MyPattern {

    public static void main(String[] args) {
        String str = "1234";
        String regex = "^[0-9]{4}$";
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(str);
        System.out.println(mat.matches());
    }

}
