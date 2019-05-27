package com.gxh.rabbitmq.work;

import com.gxh.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq work模型 消息发送，其实也simple模型发送消息的方式一样，只是接收不同
 * 能者多劳，公平分发（fair dispatch）
 * 在我开看，公平分发其实就是轮询分发加上流量控制实现的
 *
 * 自动应答的作用：避免数据丢失。
 * 设置自动应答时，无论消费者是否对消息处理成功，都会告诉队列删除消息
 * 设置手动应答时，当消费者处理完消息时，会手动返回ask通知队列删除信息，队列才会删除信息，
 * 如果一个消费者宕机，Rabbitmq将会把消息重新发送给其他监听队列的消费者
 *
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
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        for (int i=0; i< 30; i++){
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
