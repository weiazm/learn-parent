package com.hongyan.learn.common.util;

/**
 * 
 * @title SingletonCreator
 * @desc 用以完成双重检查成例实现单例模式.
 * @author purerboy
 * @date 2015年9月8日
 * @version 1.0
 */
public interface DoubleCheckSingleton<T> {
    /**
     * 创建实例的方法
     * 
     * @return
     */
    T create();

    /**
     * 获取单例的方法，用以判断单例是否创建，直接返回单例即可
     * 
     * @return
     */
    T getSingleton();

    /**
     * 给单例赋值的方法，直接给单例赋值为t即可
     * 
     * @param t
     */
    void setSingleton(T t);
}
