/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2015 All Rights Reserved.
 */
package com.hongyan.learn.dto;


/**
 * @title ResponseStatus
 * @description TODO
 * @author Macro Huang
 * @date 2015年5月5日
 * @version 1.0
 */
public enum ResponseStatus {
    ok(200), partfail(300), fail(400), server_error(500), parameter_error(600), noauth(700), server_exceed(800);
    private int code;

    private ResponseStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public ResponseStatus fromCode(int code) {
        for (ResponseStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return ok;
    }
}
