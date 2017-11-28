/*
 * Baijiahulian.com Inc. Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.service.hag;

import com.baijia.yunying.hag.dal.bo.Entity;
import com.baijia.yunying.hag.dal.bo.Resource;
import com.baijia.yunying.hag.dal.bo.Role;
import com.baijia.yunying.hag.service.HagService;

import com.google.common.collect.Lists;
import com.hongyan.learn.config.HagConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;

import lombok.SneakyThrows;

/**
 * Created by weihongyan on 9/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HagConfig.class)
public class HagServiceTest {

    /*
     * 通过小流量客户端，创建角色、资源，创建的角色和资源名称示例： train_role_{your name}{index}, train_resource{your_name}_{index}
     * ，your_name为你的姓名全拼，index从1开始 通过小流量客户端，获取并打印你创建的角色和资源列表，并判断某个测试的实体key是否属于某个角色或者某个名单 例如创建了一个姓名名单，则测试某个姓名是否在该名单
     * hag.token.id=1000003 hag.token=LnbXTykF7XspVUSAVvEj
     */
    @Value("${hag.app}")
    String app;
    @Value("${hag.token.id}")
    Long tokenId;
    @Value("${hag.token}")
    String token;
    @Autowired
    private HagService hagService;

    @Test
    @SneakyThrows
    public void test() {
        Assert.notNull(hagService);
        Resource resource = new Resource();
        resource.setApp(app);
        resource.setDescription("(￣(エ)￣)ゞ");
        resource.setName("train_resource_weihongyan_2");
        Object o = hagService.addResource(tokenId, token, resource);
        System.out.println(o);

        Role role = new Role();
        role.setApp(app);
        role.setName("train_role_weihongyan_1");
        role.setDescription("╮(╯▽╰)╭");
        role.setResExp("train_resource_weihongyan_1 | train_resource_weihongyan_2");
        Object o2 = hagService.addRole(tokenId, token, role);
        System.out.println(o2);
    }

    @Test
    @SneakyThrows
    public void test2() {
        Assert.notNull(hagService);
        List<Entity> entityList1 = Lists.newArrayList(new Entity("我爱加班"));
        List<Entity> entityList2 = Lists.newArrayList(new Entity("加班使我快乐"));
        Object o = hagService.addEntitiesToResource(tokenId, token, entityList1, "train_resource_weihongyan_1");
        Object o2 = hagService.addEntitiesToResource(tokenId, token, entityList2, "train_resource_weihongyan_2");
        System.out.println(o);
        System.out.println(o2);
    }
}
