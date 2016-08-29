/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.hongyan.learn.web.dto;

/**
 * @title ErrorSide
 * @desc TODO
 * @author macrohuang
 * @date Nov 30, 2015
 * @version 1.0
 */
public enum ErrorSide {
    CLIENT(1), SERVER(2), UNKNOW(0);
    private int code;

    private ErrorSide(int code) {
        this.code = code;
    }

    public int getErrorSideCode() {
        return code;
    }
}
