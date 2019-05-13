package com.gxh.rabbitmq.work;

import com.gxh.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * work model 公平分发 信息接收
 *
 * 在我开看，公平分发其实就是轮询分发加上流量控制实现的
 *
 */
public class WorkFair1 {
    private static final String QUEUE_NAME="test_work_queue";
    public void workReceive() throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        channel.basicQos(1);//确保一次只分发一个
        //定义一个消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body,"utf-8");
                System.out.println("[1] receive message===>"+ message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("done===>"+envelope.getDeliveryTag());
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        //设置自动应答，设置为true时，自动应答。设置为false时，收到应答需要设置回值。
        channel.basicConsume(QUEUE_NAME,false,defaultConsumer);

    }

    public static void main(String[] args) throws IOException, TimeoutException {
        WorkFair1 workReceive = new WorkFair1();
        workReceive.workReceive();
    }

}
