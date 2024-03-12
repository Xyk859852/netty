package io.netty.example.nio;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSocketChannelTest {

    public static void open() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(19999));
            Selector selector = Selector.open();
            int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
            serverSocketChannel.register(selector, interestSet);
            Set<SelectionKey> keys = selector.selectedKeys();
            System.out.println(keys);
            int select = selector.select();
            System.out.println(select);
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                System.out.println(socketChannel);
                //do something with socketChannel…
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void connect(){
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(19999));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(ServerSocketChannelTest::open);
        Thread.sleep(1000);
        executorService.submit(ServerSocketChannelTest::connect);

    }
}
