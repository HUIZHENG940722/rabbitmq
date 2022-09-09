package com.ethan.course.rabbitmq.demo.simple;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author zhenghui
 * @Description 消费者
 * @Date 2022/9/8
 */
public class Consumer {

    public static void main(String[] args) {
        // 所有的中间件技术都是基于tcp/ip协议基础之上构建新型的协议规范，只不过rabbitmq遵循的是amqp

        // 1 创建连接工程
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setPassword("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        Connection connection = null;
        connectionFactory.setVirtualHost("/");
        Channel channel = null;
        try {
            // 2 创建连接Connection
            connection = connectionFactory.newConnection("生产者");
            // 3 通过连接获取通道Channel
            channel = connection.createChannel();
            // 4 接收消息
            channel.basicConsume("queue1", true, new DeliverCallback() {
                @Override
                public void handle(String s, Delivery delivery) throws IOException {
                    System.out.println("收到的消息是" + new String(delivery.getBody(), "UTF-8"));
                }
            }, new CancelCallback() {
                @Override
                public void handle(String s) throws IOException {
                    System.out.println("接受失败了...");
                }
            });
            System.out.println("开始接受消息");
//            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5 关闭通道
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 6 关闭连接
            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
