package com.gxh.rabbitmq.work;

import com.gxh.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq work模型 消息发送，其实也simple模型发送消息的方式一样，只是接收不同
 * 能者多劳，公平分发（fair dispatch）
 *
 */
public class WorkFairSend {
    private static final String QUEUE_NAME="test_work_queue";
    public void workFairSend() throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //声明队列
        //声明队列时，指定durable为true实现消息持久化
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        /**
         * 设置参数，确保每个消费者在确认消息之前，消息队列不发送下一个消息到消费者。一次只处理一个消息
         *
         * 限制发送给同一个消费者不得超过一条消息
         */
        int prefectchCount = 1;
        channel.basicQos(prefectchCount);
        for (int i=0; i< 50; i++){
            String message = "我的未来很无敌"+i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println(message);
        }
        channel.close();
        connection.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        WorkFairSend workRoundSend = new WorkFairSend();
        workRoundSend.workFairSend();
    }
}
