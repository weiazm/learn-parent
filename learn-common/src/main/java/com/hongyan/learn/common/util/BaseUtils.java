package com.hongyan.learn.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工具类
 * 
 * @title BaseUtils
 * @desc TODO
 * @author zhangbing
 * @date 2015年3月20日
 * @version 1.0
 */
public class BaseUtils {

    /**
     * 将list对象转换成以list中对象属性作为key的MAP
     * 
     * @param list
     * @param param
     * @return
     */
    public static <K, V> Map<K, V> listToMap(List<V> list, String propertyName) {
        Map<K, V> map = new HashMap<K, V>();
        for (V v : list) {
            K key = getValue(v, propertyName);
            map.put(key, v);
        }
        return map;
    }

    /**
     * 根据对象属性名称，获取属性值
     * 
     * @param obj T
     * @param propertyName T属性
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T, V> V getValue(T obj, String propertyName) {
        V ret = null;
        try {
            Field field = obj.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            ret = (V) field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * @param objs
     * @param param 变量名称
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     */
    @SuppressWarnings("unchecked")
    public static <T, V> String getStringArray(List<T> objs, String param) {
        StringBuffer sb = new StringBuffer("(");
        try {
            for (T object : objs) {
                Field field = object.getClass().getDeclaredField(param);
                field.setAccessible(true);
                V propertyVal = (V) field.get(object);
                sb.append(propertyVal).append(",");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String ret = "";
        if (sb.toString().endsWith(",")) {
            ret = sb.substring(0, sb.length() - 1);
        }
        ret += ")";
        return ret;
    }

    /**
     * @param objs
     * @param propertyName 变量名称
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     */
    @SuppressWarnings("unchecked")
    public static <T, V> List<V> getPropertyCollections(List<T> objs, String propertyName) {
        List<V> arr = new ArrayList<V>();
        try {
            for (T object : objs) {
                Field field = object.getClass().getDeclaredField(propertyName);
                field.setAccessible(true);
                V propertyVal = (V) field.get(object);
                arr.add(propertyVal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    /**
     * 双重检查实现单例模式.
     * <p>
     * 样例代码如下：
     * </p>
     * 
     * <pre>
     * static class TestSingleton {
     *     public static TestSingleton ins;
     * 
     *     public TestSingleton() {
     *     }
     * 
     *     public static TestSingleton getIns() {
     *         setSingleton(new SingletonCreator&lt;TestSingleton&gt;() {
     * 
     *             &#064;Override
     *             public TestSingleton create() {
     *                 return new TestSingleton();
     *             }
     * 
     *             &#064;Override
     *             public TestSingleton getSingleton() {
     *                 return ins;
     *             }
     * 
     *             &#064;Override
     *             public void setSingleton(TestSingleton t) {
     *                 ins = t;
     *             }
     *         }, TestSingleton.class);
     *         return ins;
     *     }
     * }
     * </pre>
     * 
     * @param creator 创建单例的接口实现
     * @param clazz 单例的类，单例同步锁
     * @author purerboy
     */
    public static <T> void setSingleton(DoubleCheckSingleton<T> creator, Class<?> clazz) {
        T singleton = creator.getSingleton();
        if (singleton == null) {
            synchronized (clazz) {
                singleton = creator.getSingleton();
                if (singleton == null) {
                    try {
                        singleton = creator.create();
                    } finally {
                        creator.setSingleton(singleton);
                    }
                }
            }
        }
    }
    
    
}
