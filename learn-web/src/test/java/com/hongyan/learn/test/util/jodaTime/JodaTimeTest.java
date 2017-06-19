/*
 * Baijiahulian.com Inc. Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.util.jodaTime;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.util.Date;

/**
 * Created by weihongyan on 9/10/16.
 */
public class JodaTimeTest {
    @Test
    public void test() {
        DateTime dateTime = new DateTime(2012, 12, 13, 18, 23, 55);
        // DateTime dateTime = new DateTime(2011, 2, 13, 10, 30, 50, 333);
        System.out.println(dateTime.toString("MM/dd/yyyy hh:mm:ss.SSSa"));
        System.out.println(dateTime.toString("dd-MM-yyyy HH:mm:ss"));
        System.out.println(dateTime.toString("EEEE dd MMMM, yyyy HH:mm:ssa"));
        System.out.println(dateTime.toString("MM/dd/yyyy HH:mm ZZZZ"));
        System.out.println(dateTime.toString("MM/dd/yyyy HH:mm Z"));

        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"); // 时间解析
        DateTime dateTime2 = DateTime.parse("2012-12-21 23:22:45", format);
        System.out.println(dateTime2.toDate());

        // 增加 期
        DateTime dateTime1 = DateTime.parse("2012-12-03");
        dateTime1 = dateTime1.plusDays(30);
        dateTime1 = dateTime1.plusHours(3);
        dateTime1 = dateTime1.plusMinutes(3);
        dateTime1 = dateTime1.plusMonths(2);
        dateTime1 = dateTime1.plusSeconds(4);
        dateTime1 = dateTime1.plusWeeks(5);
        dateTime1 = dateTime1.plusYears(3);
        // Joda-time 各种操作.....
        dateTime1 =
            dateTime1.plusDays(1).plusYears(1).plusMonths(1).plusWeeks(1).minusMillis(1).minusHours(1).minusSeconds(1);
        // 判断是否闰
        DateTime dt4 = new DateTime();
        org.joda.time.DateTime.Property month = dt4.monthOfYear();
        System.out.println("是否闰 :" + month.isLeap());
        // 取得 3秒前的时间
        DateTime dt5 = dateTime1.secondOfMinute().addToCopy(-3);
        dateTime1.getSecondOfMinute();// 得到整分钟后,过的秒钟数
        dateTime1.getSecondOfDay();// 得到整天后,过的秒钟数
        dateTime1.secondOfMinute();// 得到分钟对象,例如做闰年判断等使
        // DateTime与java.util.Date对象,当前系统TimeMillis转换
        DateTime dt6 = new DateTime(new Date());
    }
}
