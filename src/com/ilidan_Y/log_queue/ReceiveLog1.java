package com.ilidan_Y.log_queue;

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
* @ClassName: ReceiveLog1 
* @Description: 接受日志1 
* @author ilidan_Y
* @date 2017年6月27日 上午10:59:56 
*
 */
public class ReceiveLog1 {
	
	private static final String EXCHANGE_NAME="logs";
	public static void main(String[] args) throws IOException, TimeoutException {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(ConnectionFactory.DEFAULT_HOST);
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		//创建交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		//声明一个临时队列
		String queueName = channel.queueDeclare().getQueue();
		//绑定临时队列
		channel.queueBind(queueName, EXCHANGE_NAME, "");
		
		System.out.println("1 [*] Waiting for messages. To exit press CTRL+C");
		
		//创建消费对象
		Consumer consumer = new DefaultConsumer(channel){
			//创建回掉函数
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
			}
		};
		//订阅消息(autoAck = true:需要进行消息反馈)
		channel.basicConsume(queueName, true, consumer);
		
	}

}
