/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2015 All Rights Reserved.
 */
package com.hongyan.learn.web.dto;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @title ErrorDetail
 * @description TODO
 * @author Macro Huang
 * @date 2015年5月21日
 * @version 1.0
 */
@ToString
@EqualsAndHashCode
public class ErrorDetail implements Serializable {
    /**
     * @title ErrorCode
     * @description TODO
     * @author Macro Huang
     * @date 2015年5月5日
     * @version 1.0
     */
    public static enum ErrorCode {
        invalidate_param(600, "参数不合法"), system_error(601, "系统错误");
        private int code;
        private String desc;

        private ErrorCode(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getErrorCode() {
            return code;
        }

        public String getErrorDesc() {
            return desc;
        }
    }

    /**
     *
     */
    private static final long serialVersionUID = 2090086668682261982L;
    private ErrorCode code;
    private Object detail;

    public ErrorDetail(ErrorCode code, Object detail) {
        super();
        this.code = code;
        this.detail = detail;
    }

    public int getCode() {
        return code.getErrorCode();
    }

    public Object getDetail() {
        return detail;
    }

    public String getMessage() {
        return code.getErrorDesc();
    }

}
