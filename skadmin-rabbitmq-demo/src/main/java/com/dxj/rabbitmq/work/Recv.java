package com.dxj.rabbitmq.work;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import com.dxj.rabbitmq.util.ConnectionUtil;

// 消费者1
public class Recv {
    private final static String QUEUE_NAME = "test_work_queue";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取通道
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 设置每个消费者同时只能处理一条消息
        channel.basicQos(1);
        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
                    byte[] body) throws IOException {
                // body 即消息体
                String msg = new String(body);
                System.out.println(" [消费者1] received : " + msg + "!");
                try {
                    // 模拟完成任务的耗时：1000ms
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                // 手动ACK
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 监听队列。
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
