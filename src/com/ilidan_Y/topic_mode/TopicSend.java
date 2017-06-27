package com.ilidan_Y.topic_mode;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
* @ClassName: TopicSend 
* @Description: 贾换气主题模式（模糊匹配模式）
* “*”（星号）表示一个单词
* ”#“（井号）表示零个或者多个单词
* @author ilidan_Y
* @date 2017年6月27日 下午4:10:32 
*
 */
public class TopicSend {
	
	private static final String EXCHANGE_TOPIC_MODE = "topic_";
	
	private static final String[] routingKeys = new String[]{
			"quick.orange.rabbit",   
            "lazy.orange.elephant",   
            "quick.orange.fox",   
            "lazy.brown.fox",   
            "quick.brown.fox",   
            "quick.orange.male.rabbit",   
            "lazy.orange.male.rabbit"
	};
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(ConnectionFactory.DEFAULT_HOST);
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_TOPIC_MODE, "topic");
		
		for(String key : routingKeys){
			String message = "now key is "+key;
			System.out.println("now key is "+key);
			channel.basicPublish(EXCHANGE_TOPIC_MODE, key, null, message.getBytes());
			System.out.println("send key "+key+"success!");
		}
		
		channel.close();
		connection.close();
		
	}

}
