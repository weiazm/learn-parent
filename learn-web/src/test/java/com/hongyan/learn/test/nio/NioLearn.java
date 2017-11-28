package com.hongyan.learn.test.nio;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weihongyan
 * @implNote <(▰˘◡˘▰)>
 * @since 21/11/2017 3:08 PM
 */
@Slf4j
public class NioLearn {
    private static class NioServer implements Runnable {

        private ExecutorService threadPool = Executors.newFixedThreadPool(1);

        @Override
        @SneakyThrows
        public void run() {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress(10086));
            ssc.configureBlocking(false);
            Selector acceptSelector = Selector.open();
            // 注册四种事件
            ssc.register(acceptSelector, SelectionKey.OP_ACCEPT);
            log.info("started ...");
            while (acceptSelector.select() > 0) {
                log.info("running...");
                Set<SelectionKey> selectionKeys = acceptSelector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = ssc.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);
                        log.info("接受到 accept 事件, 读取到客户端: ip:{}, port:{} ", socketChannel.socket().getInetAddress(),
                            socketChannel.socket().getPort());
                    }
                    
                    if (selectionKey.isWritable() || selectionKey.isReadable()) {
                        this.threadPool.submit(new HandleThread(selectionKey));
                    }
                    
                }
            }
        }

    }

    private static class HandleThread implements Runnable {

        private SelectionKey selectionKey;

        public HandleThread(SelectionKey selectionKey) {
            this.selectionKey = selectionKey;
        }

        @Override
        public void run() {
            handle(this.selectionKey);
        }

        @SneakyThrows
        private void handle(SelectionKey selectionKey) {
            if (selectionKey.isValid()) {

                if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    socketChannel.configureBlocking(false);
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int len = 0;
                    while (true) {
                        buffer.clear();
                        len = socketChannel.read(buffer);
                        if (len == -1)
                            break;
                        buffer.flip();
                        while (buffer.hasRemaining()) {
                            baos.write(buffer.get());
                        }
                    }
                    log.info("read from ip:{}, port:{}, content:{} ", socketChannel.socket().getInetAddress(),
                        socketChannel.socket().getPort(), new String(baos.toByteArray()));
                    socketChannel.register(selectionKey.selector(), SelectionKey.OP_WRITE, baos);
                }

                if (selectionKey.isWritable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteArrayOutputStream baos = (ByteArrayOutputStream) selectionKey.attachment();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.put(("接收到:" + new String(baos.toByteArray())).getBytes());
                    buffer.flip();
                    socketChannel.write(buffer);
                    log.info("write to ip:{}, port:{},", socketChannel.socket().getInetAddress(),
                        socketChannel.socket().getPort());
                    baos.close();
                    socketChannel.finishConnect();
                    socketChannel.close();
                }

            }
        }
    }

    public static void main(String[] args) {
        NioServer server = new NioServer();
        new Thread(server).start();
    }
}
