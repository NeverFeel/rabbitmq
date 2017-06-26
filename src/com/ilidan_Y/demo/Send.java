package com.ilidan_Y.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息发送类
 * @author ilidan_Y
 *
 */
public class Send {
	
	
	
	private static final String QUEUE_NAME = "helloMap";

	public static void main(String[] args) throws IOException, TimeoutException {
		//创建消息体
		Map<String,String> map = new HashMap<String,String>();
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
        //String message = "hello world 123!"; 
        map.put("m1", "hello world!");
        map.put("m2", "hello world 123!");
        //往队列中发出一条消息  
        channel.basicPublish("", QUEUE_NAME, null,JSON.toJSONString(map).getBytes());  
        System.out.println(" send '" + map + "'");  
        //关闭频道和连接  
        channel.close();  
        connection.close(); 
	}

}
