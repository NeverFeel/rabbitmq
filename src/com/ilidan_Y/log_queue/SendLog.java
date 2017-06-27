package com.ilidan_Y.log_queue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
* @ClassName: SendLog 
* @Description: 发送日志（模拟订阅发布）
* @author ilidan_Y
* @date 2017年6月27日 上午10:48:24 
*
 */
public class SendLog {
	
	private static final String EXCHANGE_NAME = "logs";
	public static void main(String[] args) throws IOException, TimeoutException {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(ConnectionFactory.DEFAULT_HOST);
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		//交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		for(int i=0; i<5; i++){
			String message = "Hello"+i+" rabbitmq!";
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}
		channel.close();
        connection.close();
	}

}
