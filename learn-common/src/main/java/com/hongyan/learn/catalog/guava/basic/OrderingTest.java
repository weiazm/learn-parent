/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.guava.basic;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import java.util.Collections;
import java.util.List;

/**
 * @title OrderingTest
 * @desc 链式排序器,链式调用.
 * @author hongyan
 * @date 2016年8月5日
 * @version 1.0
 */

/**
 * 创建:使用natural() 由小到大 ,usingToString() 字典排序 ,from(Comparator) 从比较器生成
 * 
 * 链子:reverse() 颠倒顺序 , nullsFirst() nullsLast() , compound(Comparator) 相等的情况怎么处理 , onResultOf(Function)计算比较值
 * 
 * Ordering对象对于集合的操作:greatestOf(iter,k) top k , leastOf(iter,k) top k , isOrdered(iter) , min/max(iter) 返回最大最小值.
 */
public class OrderingTest {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();
        list.add("hello");
        list.add("ikari");
        list.add("misado");
        list.add("appleTree");
        list.add("null");
        Collections.sort(list, Ordering.natural().reverse().nullsFirst().onResultOf(new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                Integer i = 0;
                for (char c : input.toCharArray()) {
                    i += c;
                }
                return i;
            }
        }));
        System.out.println(list);

        System.out.println(Ordering.usingToString().reverse().greatestOf(list, 2));

        System.out.println(Ordering.usingToString().reverse().onResultOf(new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return input.length();
            }
        }).compound(Ordering.natural().onResultOf(new Function<String, String>() {
            @Override
            public String apply(String input) {
                return input;
            }
        })).greatestOf(list, 4));


    }

}
