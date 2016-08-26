/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.guava.collection.set;

/**
 * @title MultiSetTest
 * @desc Multiset继承自JDK中的Collection接口，而不是Set接口。
 * @author hongyan
 * @date 2016年8月11日
 * @version 1.0
 * 
 *          当把Multiset看成普通的Collection时，它表现得就像无序的ArrayList：
 * 
 *          add(E)添加单个给定元素 iterator()返回一个迭代器，包含Multiset的所有元素（包括重复的元素） size()返回所有元素的总个数（包括重复的元素） 当把Multiset看作Map<E,
 *          Integer>时，它也提供了符合性能期望的查询操作：
 * 
 *          count(Object)返回给定元素的计数。 HashMultiset.count的复杂度为O(1)， TreeMultiset.count的复杂度为O(log n)。
 *          entrySet()返回Set<Multiset.Entry<E>>，和Map的entrySet类似。 elementSet()返回所有不重复元素的Set<E>，和Map的keySet()类似。
 *          所有Multiset实现的内存消耗随着不重复元素的个数线性增长。
 * 
 * 
 */
public class MultiSetTest {

    public static void main(String[] args) {
        System.out.println(400009 & 15);
    }

}
