package com.ilidan_Y.routing;

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
 * @ClassName: RoutingReceive1
 * @Description: receive
 * @author ilidan_Y
 * @date 2017年6月27日 下午2:38:32
 *
 */
public class RoutingReceive1 {

	// 交换器名
	private static final String EXCHANGE_NAME = "routKeyDirect";
	// 路由关键字
	private static final String[] routKeys = new String[] { "debug", "info" };
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(ConnectionFactory.DEFAULT_HOST);
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		//声明直连交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		//声明一个临时队列
		String queueName = channel.queueDeclare().getQueue();
		//多重绑定
		for(String key:routKeys){
			channel.queueBind(queueName, EXCHANGE_NAME, key);
			System.out.println("RoutingReceive1 exchange:"+EXCHANGE_NAME+", queue:"+queueName+", BindRoutingKey:" + key);  
		}
        System.out.println("RoutingReceive1 [*] Waiting for messages. To exit press CTRL+C"); 
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body,"UTF-8");
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'"); 
			}
		};
		channel.basicConsume(queueName, true, consumer); 
	}

}
