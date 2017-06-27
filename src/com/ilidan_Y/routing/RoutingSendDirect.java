package com.ilidan_Y.routing;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * @ClassName: RoutingSendDirect
 * @Description: 根据多个路由关键字【routingkey】将队列和交换器绑定起来
 * @author ilidan_Y
 * @date 2017年6月27日 下午2:25:35
 *
 */
public class RoutingSendDirect {

	// 交换器名
	private static final String EXCHANGE_NAME = "routKeyDirect";
	// 路由关键字
	private static final String[] routKeys = new String[] { "debug", "info", "warning", "error" };

	public static void main(String[] args) throws IOException, TimeoutException {

		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(ConnectionFactory.DEFAULT_HOST);
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();

		// 声明直连的路由交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");

		for (String key : routKeys) {
			String message = "Send the message level:" + key;
			channel.basicPublish(EXCHANGE_NAME, key, null, message.getBytes());
			System.out.println(" [x] Sent '" + key + "':'" + message + "'");
		}

		channel.close();
		connection.close();

	}

}
