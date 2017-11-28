package com.hongyan.learn.test.nio;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weihongyan
 * @implNote <(▰˘◡˘▰)>
 * @since 21/11/2017 5:13 PM
 */
@Slf4j
public class Clients {
    public static class ClientThread implements Runnable {

        private String send;

        public ClientThread(String send) {
            this.send = send;
        }

        @Override
        @SneakyThrows
        public void run() {
            log.info("started ....");
            SocketChannel socketChannel = SocketChannel.open();
            // 2.连接服务器
            socketChannel.connect(new InetSocketAddress(10086));

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(send.getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            socketChannel.shutdownOutput();

            // 读数据
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len = 0;
            while (true) {
                buffer.clear();
                len = socketChannel.read(buffer);
                if (len == -1)
                    break;
                buffer.flip();
                while (buffer.hasRemaining()) {
                    bos.write(buffer.get());
                }
            }
            bos.flush();

            log.info("客户端收到:" + new String(bos.toByteArray()));

            socketChannel.close();
        }
    }

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        threadPool.submit(new ClientThread("哈哈哈哈"));
//        threadPool.submit(new ClientThread("林大玉"));
//        threadPool.submit(new ClientThread("臭"));
//        threadPool.submit(new ClientThread("坑货"));
//        threadPool.submit(new ClientThread("臭林玉"));
//        threadPool.submit(new ClientThread("as地方就熬时间佛啊僵尸洞姐"));
//        threadPool.submit(new ClientThread("123123123123123"));
//        threadPool.submit(new ClientThread("收拾收拾是收拾收拾是收拾收拾是史诗实时"));
//        threadPool.submit(new ClientThread("啊"));
//        threadPool.submit(new ClientThread("到底啥发生的"));
        threadPool.shutdown();
    }
}
