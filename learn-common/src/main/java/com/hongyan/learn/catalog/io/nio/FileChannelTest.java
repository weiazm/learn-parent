/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.io.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @title FileChannelTest
 * @desc description
 * @author hongyan
 * @date 2016年8月1日
 * @version version
 */
public class FileChannelTest {
    private static RandomAccessFile raf;

    public static void main(String[] args) throws Exception {
        raf = new RandomAccessFile("/Users/hongyan/Desktop/免费版天眼系统.png", "rw");
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        FileChannel channel = raf.getChannel();

        while (channel.read(buffer) != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            buffer.clear();
        }

    }
}
