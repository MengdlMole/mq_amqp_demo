package com.mengdl.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


public class Recv {
    private final static String QUEUE_NAME = "hello";
    
    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        // Why don't we use a try-with-resource statement to automatically close the channel and the connection? By doing so we would simply make the program move on, close everything, and exit!
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    
    }   
}
