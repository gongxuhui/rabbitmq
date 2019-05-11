package com.gxh.rabbitmq.simple;

import com.gxh.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

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
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body,"utf-8");
                System.out.println("new api receive message===>"+message);
            }
        };
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,defaultConsumer);
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        SimpleReceive simpleReceive = new SimpleReceive();
        simpleReceive.receiveMessage();
    }
}
