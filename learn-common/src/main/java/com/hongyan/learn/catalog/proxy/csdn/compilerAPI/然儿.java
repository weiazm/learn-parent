/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.proxy.csdn.compilerAPI;

import lombok.Data;

/**
 * @title 然儿
 * @desc 这是我然儿
 * @author hongyan
 * @date 2016年8月8日
 * @version 1.0
 */
@Data
public class 然儿 {
    private Integer age;
    private Boolean sex;
    public void 健身() {
        System.out.println("我正在健身~啊~好累啊!");
    }
    public void 带饭() {
        System.out.println("我要下楼吃饭了,老魏你吃啥?");
    }
    private void 搞基() {
        System.out.println("我是gay!");
    }
    public static void main(String[] args) {
        然儿 傻比 = new 然儿();
        傻比.健身();
        傻比.带饭();
        傻比.搞基();
        System.out.println("this is in main!");
    }
}
