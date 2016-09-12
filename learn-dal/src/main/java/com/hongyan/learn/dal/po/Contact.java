/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.dal.po;

import com.baijia.tianxiao.sqlbuilder.annotation.*;
import lombok.Data;

/**
 * @title c
 * @desc 1+1=2
 * @author hongyan
 * @date Aug 27, 2016
 * @version 1.0
 */
@Data
@Entity(dataSourceBeanName = "h2DataSource")
@Table(catalog = "test", name = "contact")
public class Contact {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String mobile;
    @Column
    private String name;
}
