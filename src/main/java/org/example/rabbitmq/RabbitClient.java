package org.example.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * rabbit client demo
 */
public class RabbitClient {

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        // "guest"/"guest" by default, limited to localhost connections
        factory.setUsername("root");
        factory.setPassword("root");
        factory.setVirtualHost("/");
        factory.setHost("192.168.181.6");
        factory.setPort(5672);

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        String exchange = "test-fanout";
        String queueName = "fanout-queue";
        String routingKey = "fanout-routingKey";
        channel.exchangeDeclare(exchange, "fanout", true);
        channel.queueDeclare(queueName, true, false, false, null);
//        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchange, routingKey);

        byte[] messageBodyBytes = "Hello, world!".getBytes();
        channel.basicPublish(exchange, routingKey, null, messageBodyBytes);
    }

}
