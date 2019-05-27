package com.gxh.rabbitmq.topic;

import com.gxh.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主题模式 消息发送  topic model
 *
 * 路由模式是通过routing key精确匹配，主题模式是通过routing key 进行模糊匹配
 *
 * 在 topic 模式下支持两个特殊字符匹配
 * *（星号） 代表一个单词
 * #（井号） 0个或者多个单词
 **/
public class TopicSend {
    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //申明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String routingKey = "";
        String message = "巩旭辉今天到此一游";
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,message.getBytes());
        channel.close();
        connection.close();
    }
}
