package com.gxh.rabbitmq.simple;

import com.gxh.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq simple模型 接收消息
 */
public class SimpleReceive {
    private static final String QUEUE_NAME="test_simple_queue";

    public void receiveMessage() throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //定义队列的消费者
        //DefaultConsumer defaultConsumer = new DefaultConsumer(channel);



    }
}
