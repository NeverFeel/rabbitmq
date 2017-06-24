package com.ilidan_Y.demo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息发送类
 * @author ilidan_Y
 *
 */
public class Send {
	
	
	
	private static final String QUEUE_NAME = "hello";

	public static void main(String[] args) throws IOException, TimeoutException {
		//创建连接工厂
		ConnectionFactory connectionFactory = new ConnectionFactory();
		//设置主机地址
		connectionFactory.setHost(ConnectionFactory.DEFAULT_HOST);
		//创建一个连接
		Connection connection = connectionFactory.newConnection();
		//创建一个频道
		Channel channel = connection.createChannel();
		//制定一个消息队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//发送的消息  
        String message = "hello world!";  
        //往队列中发出一条消息  
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());  
        System.out.println(" send '" + message + "'");  
        //关闭频道和连接  
        channel.close();  
        connection.close(); 
	}

}
