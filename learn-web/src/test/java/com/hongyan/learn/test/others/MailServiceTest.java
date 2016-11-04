/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.others;

import com.hongyan.learn.config.MailSenderConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by weihongyan on 04/11/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MailSenderConfig.class)
public class MailServiceTest {
    @Autowired
    private MailSender mailSender;

    @Test
    public void sendMail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("weiazm@163.com");
        message.setTo("995227004@qq.com");
        message.setSubject("test");
        message.setSentDate(new Date());
        message.setText("fuck");
        mailSender.send(message);
    }
}
