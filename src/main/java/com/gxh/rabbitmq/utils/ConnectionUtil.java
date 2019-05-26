package com.gxh.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 获取rabbitmq数据库连接的工具类
 */
public class ConnectionUtil {
    /**
     * 获取rabbitmq连接
     * @return
     */
    public  static Connection getConnection() throws IOException, TimeoutException {
        //定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //factory.setHost("127.0.0.1");
        factory.setHost("192.168.80.109");
        factory.setPort(5672);
        factory.setVirtualHost("/admin");
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        return connection;
    }


}
