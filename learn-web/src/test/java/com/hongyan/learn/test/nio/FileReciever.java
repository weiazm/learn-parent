/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.test.nio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * Created by weihongyan on 9/22/16.
 */
@Slf4j
public class FileReciever {
    @SneakyThrows
    public static void main(String[] args) {
        ServerSocket server = new ServerSocket(9999);
        log.info("listening port 9999  waiting to connect...");
        Socket socket = server.accept();
        log.info("connected ...");
        SocketChannel channel = socket.getChannel();
        log.info("get channel from socket...");


    }
}
