/*
 * Baijiahulian.com Inc. Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.javaBase.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by weihongyan on 9/22/16.
 */
@Slf4j
public class FileSender {
    @SneakyThrows
    public static void main(String[] args) {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress(9999));
        log.info("get channel from 9999");
        String msg = "hello !\n";
        ByteBuffer buffer = ByteBuffer.allocate(msg.getBytes().length);
        buffer.put(msg.getBytes());
        buffer.flip();// remember to flip after write
        channel.write(buffer);
        channel.finishConnect();
        channel.close();
        log.info("send done!");
    }
}
