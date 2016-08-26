/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.javabase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @title HyMarkBean
 * @desc description
 * @author hongyan
 * @date 2016年7月27日
 * @version version
 */
@Data
@EqualsAndHashCode
public class HyMarkBean {
    @HyMark(num = 3, tag = "第一个id", ifPrint = true)
    private Integer id;
    @HyMark(num = 2, tag = "第二个id", ifPrint = true)
    private Integer id2;
    @HyMark(num = 1, tag = "第三个id", ifPrint = false)
    private Integer id3;
    @HyMark(num = 5, tag = "第一个name", ifPrint = true)
    private String name;
    @HyMark(num = 4, tag = "第二个name", ifPrint = true)
    private String name2;
    @HyMark(num = 4, tag = "第三个name", ifPrint = true)
    private String name3;
    
    @SuppressWarnings("unused")
    private void fuck(){
        System.out.println("老子他妈的是私有的");
    }
}
