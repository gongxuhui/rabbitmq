package com.gxh.rabbitmq.routing;

import com.gxh.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式 routing model 消息发送
 *
 * 路由模式其实和订阅模式差不多，只不过交换机的类型不同而已。
 * 生产者申明一个direct类型交换机，然后发送消息到这个交换机指定路由键，
 * 消费者指定消费这个交换机的这个路由键，即可接收到消息，其他消费者收不到。
 *
 * 路由模式对应的交换机类型为direct
 */
public class Send {
    private static final String EXCHANGE_NAME="test_exchange_direct";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        String msg = "未来的模样";
        String routringKey = "error";
        channel.basicPublish(EXCHANGE_NAME,routringKey,null,msg.getBytes());
        System.out.println("发送消息===>"+msg);
        channel.close();
        connection.close();
    }
}
