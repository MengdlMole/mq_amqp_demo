package com.mengdl.publisher;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Send {
    private final static String QUEUE_NAME = "hello";
    private static Logger logger = LogManager.getLogger(Send.class);
    
    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        // If we wanted to connect to a node on a different machine we'd simply specify its hostname or IP address here.
        factory.setHost("localhost");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                String message = "Hello World!";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
        }
        catch(Exception e){
            logger.error("publish message error", e);
        }
        finally{
            // channel.close();
            // connection.close();
        }
    }
}
