package com.gxh.rabbitmq.work;

import com.gxh.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * work model 信息接收
 */
public class WorkReceive1 {
    private static final String QUEUE_NAME="test_work_queue";
    public void workReceive() throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
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
                    System.out.println("done");
                }
            }
        };
        channel.basicConsume(QUEUE_NAME,true,defaultConsumer);

    }

    public static void main(String[] args) throws IOException, TimeoutException {
        WorkReceive1 workReceive = new WorkReceive1();
        workReceive.workReceive();
    }

}
