package com.ilidan_Y.demo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * 接受信息
 * 
 * @author ilidan_Y
 *
 */
public class Receive {
	// 队列名称
	private final static String QUEUE_NAME = "helloMap";

	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException,
			ConsumerCancelledException, InterruptedException {

		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println("waiting for message...");

		// 创建队列消费者
		//QueueingConsumer consumer = new QueueingConsumer(channel);
		// 指定消费队列
//		channel.basicConsume(QUEUE_NAME, true, consumer);
//		while (true) {
//			// nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
//			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
//			String message = new String(delivery.getBody());
//			System.out.println(" [x] Received '" + message + "'");
//		}
		
		//创建消费者
//      DefaultConsumer类实现了Consumer接口，通过传入一个频道，告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery 
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("Received '" + message + "'");
			}
		};
//      自动回复队列应答
		channel.basicConsume(QUEUE_NAME, true, consumer);

	}

}
