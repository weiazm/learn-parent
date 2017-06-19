package com.hongyan.learn.test.elsRESTfulAPI.dto;

import java.util.List;

import lombok.Data;

/**
 * @author weihongyan
 * @description TODO
 * @date 14/02/2017
 */
@Data
public class Employee {
    private String firstName;
    private String lastName;
    private Integer age;
    private String about;
    private List<String> interests;
}
