package com.gxh.rabbitmq.routing;

import com.gxh.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式 消息接收 routing model
 */
public class Receive2 {
    private static final String EXCHANGE_NAME="test_exchange_direct";
    private static final String QUEUE_NAME="test_queue_direct2";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 绑定交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"error");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"info");
        //定义一个消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("[2]接收到到信息===>"+msg);
                //关闭自动答复
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //设置自动应答，设置为true时，自动应答。设置为false时，收到应答需要设置回值。
        channel.basicConsume(QUEUE_NAME, false, defaultConsumer);
    }

}
