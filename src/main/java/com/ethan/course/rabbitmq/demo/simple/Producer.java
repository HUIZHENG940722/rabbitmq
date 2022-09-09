package com.ethan.course.rabbitmq.demo.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author zhenghui
 * @Description 生产者
 * @Date 2022/9/8
 */
public class Producer {

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
            // 4 通过通道创建交换机，声明队列，绑定关系，路由key，发送消息和接受消息
            String queueName = "queue1";
            /**
             * @param1 队列名称
             * @param2 是否要持久化durable=false 所谓持久化消息是否存盘，如果false 非持久化 true 持久化？非持久化会存盘吗？
             * @param3 排他性，是否是独占独立
             * @param4 是否自动删除，随着最后一个消息消费完毕是否把队列删除
             * @param5 附带属性
             */
            channel.queueDeclare(queueName, false, false, false, null);
            // 5 准备消息内容
            String message = "Hello xuexiangban!!!";
            // 6 发送消息给队列queue
            /**
             * @param1 交换机
             * @param2 
             */
            channel.basicPublish("", queueName, null, message.getBytes());
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
