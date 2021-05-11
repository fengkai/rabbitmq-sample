package org.example.rabbitmq.pictures;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Receive picture from rabbitmq.Then send to client use stomp over websocket.
 */
@Component
public class PictureReceiver implements CommandLineRunner {

    private static final String EXCHANGE_NAME = "picture-test";

    public static final String QUEUE_NAME="my-queue2";

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void run(String... args) throws Exception {
        //create exchange
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("root");
        factory.setPassword("root");
        factory.setVirtualHost("/");
        factory.setHost("192.168.181.6");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        //Exclusive (used by only one connection and the queue will be deleted when that connection closes)
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"all");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'"  + "'");

            //send to websocket client
            simpMessagingTemplate.convertAndSend("/topic/greeting",message);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
