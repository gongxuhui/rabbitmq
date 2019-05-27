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
 * 流量监控--->basic.qos prefetch-count（前提：流量控制只有在消费者开始acknowledge之后才生效）
 *
 * prefetch与消息投递
 * prefetch允许为每个consumer指定最大的unacked messages数目。简单来说就是用来指定一个consumer一次可以
 * 从Rabbit中获取多少条message并缓存在client中(RabbitMQ提供的各种语言的client library)。一旦缓冲区满了，
 * Rabbit将会停止投递新的message到该consumer中直到它发出ack
 *
 * 假设prefetch值设为10，共有两个consumer。意味着每个consumer每次会从queue中预抓取 10 条消息到本地缓存着等待消费。
 * 同时该channel的unacked数变为20。而Rabbit投递的顺序是，先为consumer1投递满10个message，再往consumer2投递10个message。
 * 如果这时有新message需要投递，先判断channel的unacked数是否等于20，如果是则不会将消息投递到consumer中，message继续呆在queue中。
 * 之后其中consumer对一条消息进行ack，unacked此时等于19，Rabbit就判断哪个consumer的unacked少于10，就投递到哪个consumer中。
 *
 * 总的来说，consumer负责不断处理消息，不断ack，然后只要unacked数少于prefetch * consumer数目，broker就不断将消息投递过去
 *
 *
 *
 */
public class WorkFair1 {
    private static final String QUEUE_NAME="test_work_queue";
    public void workReceive() throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        /**
         * 设置参数，确保每个消费者在确认消息之前，消息队列不发送下一个消息到消费者。一次只处理一个消息
         *
         * 限制发送给同一个消费者不得超过一条消息
         */
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
