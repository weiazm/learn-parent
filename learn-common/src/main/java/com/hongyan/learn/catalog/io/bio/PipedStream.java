/**
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.hongyan.learn.catalog.io.bio;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import lombok.Getter;

/**
 * @title PipedStream
 * @desc description
 * @author hongyan
 * @date 2016年8月1日
 * @version version
 */
public class PipedStream {
    @Getter
    static class Outputer implements Runnable {
        private PipedOutputStream pos = null;

        public Outputer() {
            pos = new PipedOutputStream();
        }

        @Override
        public void run() {
            try {
                pos.write("hahaha".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                pos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Getter
    static class Inputer implements Runnable {
        private PipedInputStream pis = null;

        public Inputer() {
            pis = new PipedInputStream();
        }

        @Override
        public void run() {
            try {
                byte[] buffer = new byte[100];
                pis.read(buffer);
                System.out.println(new String(buffer));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                pis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws IOException {
        Outputer out = new Outputer();
        Inputer in = new Inputer();

        out.getPos().connect(in.getPis());

        new Thread(out).start();
        new Thread(in).start();

    }
}
