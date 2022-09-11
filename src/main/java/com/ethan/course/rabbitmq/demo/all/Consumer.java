package com.ethan.course.rabbitmq.demo.all;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author zhenghui
 * @Description 消费者
 * @Date 2022/9/9
 */
public class Consumer {
    public static void main(String[] args) {
        // 1 创建连接工程
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setPassword("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = null;
        Channel channel = null;
        // 2 获取队列名称
        final String queueName = Thread.currentThread().getName();
        try {
            // 3 从连接工厂中获取连接
            connectionFactory.newConnection("消费者");
            // 4 从连接中获取通道
            channel = connection.createChannel();
            // 5 定义接受消息的回调
            Channel finalChannel = channel;
            finalChannel.basicConsume(queueName, true, new DeliverCallback() {
                @Override
                public void handle(String s, Delivery delivery) throws IOException {
                    System.out.println(delivery.getEnvelope().getDeliveryTag());
                    System.out.println(queueName + "：收到消息是：" + new String(delivery.getBody(), "UTF-8"));
                }
            }, new CancelCallback() {
                @Override
                public void handle(String s) throws IOException {

                }
            });
            System.out.println(queueName + "：开始接受消息");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送消息出现异常");
        } finally {
            // 6 关闭通道
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 7 关闭连接
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
