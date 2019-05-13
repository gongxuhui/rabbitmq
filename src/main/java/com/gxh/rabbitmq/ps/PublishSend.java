package com.gxh.rabbitmq.ps;

import com.gxh.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq 发布/订阅模式 （publish/subscribe model）发送消息
 * 注意：交换机没有存储数据的能力，在rabbitmq中只有队列有存储能力
 *
 */
public class PublishSend {
    private static final String EXCHANGE_NAME ="test_exchange_fanout";
    public void sendMessage() throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String message = "今天搞定发布订阅模式";
        channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
        System.out.println("Send===>"+message);
        channel.close();
        connection.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        PublishSend publishSend = new PublishSend();
        publishSend.sendMessage();
    }
}
