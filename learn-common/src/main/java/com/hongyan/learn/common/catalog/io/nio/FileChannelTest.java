/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.common.catalog.io.nio;

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
        raf = new RandomAccessFile("/Users/weihongyan/Desktop/go.sh", "rw");
        ByteBuffer buffer = ByteBuffer.allocate((int)raf.length());
        FileChannel channel = raf.getChannel();

        channel.read(buffer);
        buffer.flip();
        System.out.println(new String(buffer.array()));

//        while (channel.read(buffer) != -1) {
//            buffer.flip();//remember to flip before get
//            while (buffer.hasRemaining()) {
//                System.out.print((char) buffer.get());
//            }
//            buffer.clear();//remember to clear after using
//        }

    }
}
