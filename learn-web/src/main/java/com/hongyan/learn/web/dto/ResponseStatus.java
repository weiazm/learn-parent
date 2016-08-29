/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2015 All Rights Reserved.
 */
package com.hongyan.learn.web.dto;

/**
 * @title ResponseStatus
 * @description TODO
 * @author Macro Huang
 * @date 2015年5月5日
 * @version 1.0
 */
public enum ResponseStatus {
    fail(400), noauth(700), ok(200), parameter_error(600), partfail(300), server_error(500), server_exceed(800);
    private int code;

    private ResponseStatus(int code) {
        this.code = code;
    }

    public ResponseStatus fromCode(int code) {
        for (ResponseStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return ok;
    }

    public int getCode() {
        return code;
    }
}
