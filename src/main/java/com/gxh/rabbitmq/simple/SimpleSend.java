package com.gxh.rabbitmq.simple;

import com.gxh.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *  rabbitmq simple模型 发送消息
 */
public class SimpleSend {

    private static final String QUEUE_NAME="test_simple_queue";

    public void sendMessage() throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "我的未来会更好";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("发送信息为===>"+message);
        channel.close();
        connection.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        SimpleSend simpleSend = new SimpleSend();
        simpleSend.sendMessage();
    }
}
