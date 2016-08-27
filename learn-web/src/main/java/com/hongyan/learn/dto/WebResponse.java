/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2015 All Rights Reserved.
 */
package com.hongyan.learn.dto;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @title WebResponse
 * @description 与前端约定的返回通用对象
 * @author Macro Huang
 * @date 2015年5月5日
 * @version 1.0
 */
@ToString
@EqualsAndHashCode
public class WebResponse<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -1295557245590009278L;
    private ResponseStatus status;
    private ErrorDetail error;
    private T data;

    public WebResponse() {
        status = ResponseStatus.ok;
    }

    public int getStatus() {
        return status == null ? ResponseStatus.ok.getCode() : status.getCode();
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public ErrorDetail getError() {
        return error;
    }

    public void setError(ErrorDetail error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
