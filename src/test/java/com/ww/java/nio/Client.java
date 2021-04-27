package com.ww.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class Client {

    public static void main(String[] args) {
        int threadNum = 10;

        List<Thread> threadList = new ArrayList<>(threadNum);

        Client client = new Client();
        for (int i = 1; i <= threadNum; i++) {
            threadList.add(new Thread(() -> {
                try {
                    client.connectServerAndSendMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, "Thread-" + i));
        }

        for (Thread thread : threadList) {
            thread.start();
        }
    }

    private void connectServerAndSendMessage() throws IOException {
        String threadName = Thread.currentThread().getName();

        // 得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        // 设置为非阻塞
        socketChannel.configureBlocking(false);
        // 提供服务器端的IP和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        // 连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println(threadName + "：连接需要时间,客户端不会阻塞...先去吃个宵夜");
            }
        }

        // 连接成功,发送数据
        String str = "Hello,I'm " + threadName;
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(byteBuffer);
        socketChannel.close();
        System.out.println(threadName + "：客户端退出");
    }

}