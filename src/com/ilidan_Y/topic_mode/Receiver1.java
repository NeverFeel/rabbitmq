package com.ilidan_Y.topic_mode;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * 
 * @ClassName: Receiver1
 * @Description: mode1
 * @author ilidan_Y
 * @date 2017年6月27日 下午4:52:47
 *
 */
public class Receiver1 {

	private static final String EXCHANGE_NAME = "topic_";

	private static final String[] mode = new String[] { "*.orange.*" };

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(ConnectionFactory.DEFAULT_HOST);
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "topic");

		String queueName = channel.queueDeclare().getQueue();
		for (String keyMode : mode) {

			channel.queueBind(queueName, EXCHANGE_NAME, keyMode);
			System.out.println("ReceiveLogsTopic2 exchange:" + EXCHANGE_NAME + ", queue:" + queueName
					+ ", BindRoutingKey:" + keyMode);

		}
		System.out.println("ReceiveLogsTopic2 [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out
						.println("ReceiveLogsTopic2 [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");

			}
		};

		channel.basicConsume(queueName, true, consumer);

	}
}
