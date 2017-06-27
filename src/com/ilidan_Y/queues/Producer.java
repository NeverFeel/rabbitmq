package com.ilidan_Y.queues;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * 
* @ClassName: Producer 
* @Description: 消息生产者
* @author ilidan_Y
* @date 2017年6月26日 下午12:05:38 
*
 */
public class Producer {
	
	private static final String TASK_QUEUE_NAME = "task";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(ConnectionFactory.DEFAULT_HOST);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		/**
		 * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,
         *                       Map<String, Object> arguments) 
         *                       queue:队列名
         *                       durable:声明一个持久化队列，mq的服务不重启，队列就一直存在
         *                       exclusive：声明一个独占式队列，占据一个连接
         *                       autoDelete：声明一个自动删除队列，队列不用时由服务器自动删除
		 */
		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		for(int i=0; i<5; i++){
			String message = "this is messgae "+i;
			channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			System.out.println(" sent '" + message + "'");
		}
		channel.close();
		connection.close();
		
	}

}
