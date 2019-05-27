package com.gxh.rabbitmq.topic;

import com.gxh.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主题模式   消息接收
 *
 * 在 topic 模式下支持两个特殊字符匹配
 * *（星号） 代表一个单词
 * #（井号） 0个或者多个单词
 **/
public class Receive3 {
    private static final String QUEUE_NAME = "test_queue_topic_3";
    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 申明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"#.info");
        // 创建一个回调的消费者处理类
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 接收到的消息
                String message = new String(body);
                System.out.println(" [3] Received '" + message + "'");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [3] done ");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        //设置自动应答，设置为true时，自动应答。设置为false时，收到应答需要设置回值。
        channel.basicConsume(QUEUE_NAME, false, consumer);

    }
}
