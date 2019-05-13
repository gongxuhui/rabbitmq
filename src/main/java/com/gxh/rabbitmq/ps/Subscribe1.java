package com.gxh.rabbitmq.ps;

import com.gxh.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * rabbitmq 发布/订阅模式  接收信息
 */
public class Subscribe1 {
    private static final String EXCHANGE_NAME ="test_exchange_fanout";
    private static final String QUEUE_NAME = "test_queue_email";

    public void subscribeReceive() throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
        //定义一个消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body, "utf-8");
                System.out.println("[1] receive message===>" + message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("done===>" + envelope.getDeliveryTag());
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        //设置自动应答，设置为true时，自动应答。设置为false时，收到应答需要设置回值。
        channel.basicConsume(QUEUE_NAME, false, defaultConsumer);
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Subscribe1 subscribe1 = new Subscribe1();
        subscribe1.subscribeReceive();
    }


}
