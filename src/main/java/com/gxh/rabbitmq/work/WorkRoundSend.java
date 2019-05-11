package com.gxh.rabbitmq.work;

import com.gxh.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq work模型 消息发送，其实也simple模型发送消息的方式一样，只是接收不同
 * 无论两个客户端处理业务的时间长短，消息都是平均分发的。这种方式叫做轮询分发（round-robin）
 */
public class WorkRoundSend {
    private static final String QUEUE_NAME="test_work_queue";
    public void workSend() throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        for (int i=0; i< 50; i++){
            String message = "我的未来很无敌"+i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println(message);
        }
        channel.close();
        connection.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        WorkRoundSend workRoundSend = new WorkRoundSend();
        workRoundSend.workSend();
    }
}
