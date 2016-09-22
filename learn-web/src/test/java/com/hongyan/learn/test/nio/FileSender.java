/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.nio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by weihongyan on 9/22/16.
 */
@Slf4j
public class FileSender {
    @SneakyThrows
    public static void main(String[] args) {
        Socket socket = new Socket("localhost",9999);
        log.info("connect to localhost:9999...");
        SocketChannel channel = socket.getChannel();
        log.info("get channel form socket...");

        String msg = "hello !";
        ByteBuffer buffer = ByteBuffer.allocate(msg.getBytes().length);
        buffer.put(msg.getBytes());
        channel.write(buffer);
        channel.close();
    }
}
