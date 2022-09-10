package com.ethan.course.rabbitmq.demo.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author zhenghui
 * @Description 生产者
 * @Date 2022/9/9
 */
public class Producer {

    public static void main(String[] args) {
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
            // 4 准备消息内容
            String message = "Hello xuexiangban!!!";
            // 5 准备交换机
            String exchangeName = "amq.topic";
            // 5.1 定义routeKey
            String routeKey = "com.ethan.**";
            // 5.2 指定交换机类型
            String type = "topic";
            // 6 发送消息给交换机
            /**
             * @param1 交换机
             * @param2 队列名称/routingKey
             * @param3 属性配置
             * @param4 发送消息的内容
             */
            channel.basicPublish(exchangeName, routeKey, null, message.getBytes());
            System.out.println("发送消息成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7 关闭通道
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 8 关闭连接
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
