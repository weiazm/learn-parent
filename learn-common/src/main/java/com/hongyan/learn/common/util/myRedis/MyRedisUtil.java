/*
 * Baijiahulian.com Inc. Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.common.util.myRedis;

import com.google.gson.Gson;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by weihongyan on 9/20/16.
 */
@Slf4j
public class MyRedisUtil {

    private static Gson gson = new Gson();
    private static StringRedisSerializer serializer = new StringRedisSerializer();

    private MyRedisUtil() {
    }

    public static Long rightAddList(RedisConnection connection, String key, Object value) {
        return connection.rPush(serialize(key), serialize(value));
    }

    public static Long leftAddList(RedisConnection connection, String key, Object value) {
        return connection.lPush(serialize(key), serialize(value));
    }

    public static Object rightGetList(RedisConnection connection, String key, Class<? extends Object> clazz) {
        return deSerialize(connection.rPop(serialize(key)), clazz);
    }

    public static Object leftGetList(RedisConnection connection, String key, Class<? extends Object> clazz) {
        return deSerialize(connection.lPop(serialize(key)), clazz);
    }

    public static Boolean putMap(RedisConnection connection, String key, Object field, Object value) {
        return connection.hSet(serialize(key), serialize(field), serialize(value));
    }

    public static Object getMap(RedisConnection connection, String key, Object field, Class<?> clazz) {
        return deSerialize(connection.hGet(serialize(key), serialize(field)), clazz);
    }

    public static byte[] serialize(Object value) {
        return serializer.serialize(gson.toJson(value));
    }

    public static Object deSerialize(byte[] bytes, Class<?> clazz) {
        return gson.fromJson(serializer.deserialize(bytes), clazz);
    }

}
