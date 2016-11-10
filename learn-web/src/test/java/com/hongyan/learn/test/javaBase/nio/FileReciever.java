/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.javaBase.nio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by weihongyan on 9/22/16.
 */
@Slf4j
public class FileReciever {
    @SneakyThrows
    public static void main(String[] args) {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(9999));
        log.info("prepare to be connected from port 9999...");
        SocketChannel channel = server.accept();
        log.info("get channel from socket...");
        ByteBuffer buffer = ByteBuffer.allocate(2);
        while (channel.read(buffer) != -1) {
            buffer.flip();
            System.out.print(new String(buffer.array()));
            buffer.clear();
        }
        log.info("recieve done!");
    }
}
