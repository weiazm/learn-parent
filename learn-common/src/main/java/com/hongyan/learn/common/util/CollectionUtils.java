/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.hongyan.learn.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @title CollectionUtils
 * @desc TODO
 * @author taoyaping
 * @date 2015年3月12日
 * @version 1.0
 */
public class CollectionUtils {

    public static <K, E> List<K> extractList(Collection<E> objs, Extracter<K, E> extracter) {
        List<K> list = new ArrayList<K>();
        for (E obj : objs) {
            K key = (K) extracter.extract(obj);
            list.add(key);
        }
        return list;
    }

    public static <K, E> Map<K, E> extractMap(Collection<E> objs, Extracter<K, E> extracter) {
        Map<K, E> map = new HashMap<K, E>();
        for (E obj : objs) {
            K key = (K) extracter.extract(obj);
            map.put(key, obj);
        }
        return map;
    }

    public static <K, E> Map<K, List<E>> extractMappedList(Collection<E> objs, Extracter<K, E> extracter) {
        Map<K, List<E>> map = new HashMap<K, List<E>>();
        for (E obj : objs) {
            K key = (K) extracter.extract(obj);
            List<E> items = map.get(key);
            if (null == items) {
                items = new LinkedList<E>();
                map.put(key, items);
            }
            items.add(obj);
        }
        return map;
    }

    public interface Extracter<K, E> {
        K extract(E obj);
    }

    /**
     * 按指定大小拆分集合，按原集合顺序拆分
     * 
     * @param orig 原集合
     * @param batchSize 每个集合大小
     * @return
     */
    public static <T> List<List<T>> split(Collection<T> orig, int batchSize) {
        if (orig == null || orig.isEmpty() || batchSize < 1) {
            return Collections.emptyList();
        }
        int size = orig.size();
        int len = calSplitLen(batchSize, size);
        List<List<T>> result = new ArrayList<>(len);
        List<T> list = null;
        if (orig instanceof List) {
            list = (List<T>) orig;
        } else {
            list = new ArrayList<>(orig);
        }
        for (int i = 0; i < len; i++) {
            result.add(new ArrayList<>(list.subList(i * batchSize, ((i + 1) * batchSize) < size ? (i + 1) * batchSize
                : size)));
        }
        return result;
    }

    /**
     * 按原集合顺序合并集合
     * 
     * @param splits 待合并集合
     * @return
     */
    public static <T, C extends Collection<T>> List<T> merge(Collection<C> splits) {
        if (splits == null || splits.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> list = new ArrayList<>();
        for (Collection<T> split : splits) {
            list.addAll(split);
        }
        return list;
    }

    private static int calSplitLen(int batchSize, int size) {
        return ((size - 1) / batchSize) + 1;
    }

}
