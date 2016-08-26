/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @title PatternTest
 * @desc description
 * @author hongyan
 * @date 2016年8月2日
 * @version version
 */
public class PatternTest {
    /**
     * 写一段代码将tianxiao-service中所有PO的数据库名称和表名列出来，要求如下：
每个模块一个文件。文件名是模块名.txt
每个类写一行，格式如下： 类名：OrgInfo 数据库：yunying 表名：org_info
 @Data
@Table(name = "org_info", catalog = "yunying")   //name表示表名，catelog表示数据库名称，有的表名在@Entity的name属性中
@Entity
public class OrgInfo {
     * @param args
     */

    public static void main(String[] args) {
//        Pattern pattern = Pattern.compile("^se/w");
//        String str = "abcsewexcwwabcew";
//        Matcher matcher = pattern.matcher(str);
//        System.out.println(matcher.matches());
//
//        String[] strs = pattern.split(str);
//        System.out.println(Arrays.toString(strs));
//        for (String string : strs) {
//            System.out.println(string.length());
//        }


        String text =
            "<textarea rows=\"20\" cols=\"70\">nexus maven repository index properties updating index central</textarea>";
        // 下面的正则表达式中共有二个捕获组：(.*?)和整个匹配到的内容，两个非捕获组:(?:</textarea>)和(?:<textarea.*?>)
        String reg = "(?:<\\w+.*?>)(.*?)(?:</\\1>)";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(text);
        while (m.find()) {
            System.out.println(m.group(0)); // 整个匹配到的内容
            System.out.println(m.group(1)); // (.*?)
        }

        testCharacter();
    }

    public static void testCharacter() {
        String str =
            "se2323sew.ser[q23/sdwe\\3223<html> xm.chen@126.com 192.168.1.1 255.255.255.255 256.198.198.198 300.1.1.1 sw xm_chen2005@126.com <div> sewe </div>eeeewe</html>abc@123.com";

        patternMatch(Pattern.compile("[\\w\\._-]+?@[\\w_-]+\\.[\\w]+"), str);
        patternMatch(Pattern.compile("(\\d{1,3}\\.){2}\\d{1,3}"), str);
        patternMatch(Pattern.compile("https?://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?"), str);
        patternMatch(Pattern.compile("<(\\w+)>.+?</\\1>"), str); // 思考一下，如何匹配最细粒度的html标签和标签中的内容

    }

    private static void patternMatch(Pattern pattern, String str) {
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out
                .println(matcher.group() + " start index:" + matcher.start() + " group count:" + matcher.groupCount());
        }
    }
}
