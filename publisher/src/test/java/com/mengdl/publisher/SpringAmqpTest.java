package com.mengdl.publisher;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void testSendMesasage2Queue(){
        String QUEUE_NAME = "simple.queue";
        String msg = "Hello, amqp!";
        rabbitTemplate.convertAndSend(QUEUE_NAME, null, msg);
    }

    @Test
    void testWorkQueue() throws InterruptedException{
        String QUEUE_NAME = "work.queue";
        for(int i = 0; i < 50; i++){
            String msg = "Hello, worker, message_! " + i;
            rabbitTemplate.convertAndSend(QUEUE_NAME, msg);
            Thread.sleep(20);
        } 
    }

    @Test
    void testSendMesasage2Fanout(){
        String Exchange_Name = "hmall.fanout";
        String msg = "Hello, everyone!";
        rabbitTemplate.convertAndSend(Exchange_Name, null, msg);
    }

    @Test
    void testSendMesasage2Direct(){
        String Exchange_Name = "hmall.direct";
        String msgRed = "Hello, red!";
        rabbitTemplate.convertAndSend(Exchange_Name, "red", msgRed);
        String msgBlue = "Hello, blue!";
        rabbitTemplate.convertAndSend(Exchange_Name, "blue", msgBlue);
        String msgYellow = "Hello, yellow!";
        rabbitTemplate.convertAndSend(Exchange_Name, "yellow", msgYellow);
    }

    @Test
    void testSendMesasage2Topic(){
        String Exchange_Name = "hmall.topic";
        String msg1 = "Hello, china news!";
        rabbitTemplate.convertAndSend(Exchange_Name, "china.news", msg1);
        String msg2 = "Hello, china weathers!";
        rabbitTemplate.convertAndSend(Exchange_Name, "china.weathers", msg2);
        String msg3 = "Hello, other news!";
        rabbitTemplate.convertAndSend(Exchange_Name, "other.news", msg3);
        String msg4 = "Hello, other weathers!";
        rabbitTemplate.convertAndSend(Exchange_Name, "other.weathers", msg4);
    }

    @Test
    void testSendObject(){
        // String Exchange_Name = "hmall.object";
        String Queue_Name = "object.queue";
        Map<String, Object> msg = new HashMap<>();
        msg.put("name", "mengdl");
        msg.put("age", 18);
        rabbitTemplate.convertAndSend(Queue_Name, msg);
        // rabbitTemplate.convertAndSend(Exchange_Name, "object", msg);
    }

    @Test
    void testConfirmCallback() throws InterruptedException{
        // 1.创建correlationData对象
        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
        // 2.添加ConfirmCallback
        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onFailure(Throwable ex){
                log.error("消息发送失败: ", ex);
            }

            @Override
            public void onSuccess(CorrelationData.Confirm result){
                log.debug("收到ConfirmCallback回执: ");
                if(result.isAck()){
                    log.debug("消息发送成功，收到ACK");
                }
                else{
                    log.error("消息发送失败，收到NACK，原因: {}", result.getReason());
                }
            }
        });
        rabbitTemplate.convertAndSend("hmall.direct", "red", "Hello, red!", cd);
        Thread.sleep(5000);
    }

    @Test
    void testPageOut() throws InterruptedException{
        Message msg = MessageBuilder
            .withBody("Hello".getBytes(StandardCharsets.UTF_8))
            .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT).build();
        rabbitTemplate.convertAndSend("simple.queue", null, msg);
    }


    @Test
    void testSendTTLMessage() throws InterruptedException{
        Message msg = MessageBuilder
            .withBody("Hello".getBytes(StandardCharsets.UTF_8))
            .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT)
            .setExpiration("10000")
            .build();
        // rabbitTemplate.convertAndSend("simple.direct", "dlx", msg);
        rabbitTemplate.convertAndSend("simple.direct", "dlx", "hello",new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message){
                message.getMessageProperties().setExpiration("10000");
                return message;
            }
        });
        log.info("消息发送成功");
    }

    @Test
    void testPublisherDelayMessage() throws InterruptedException{
        String msg = "hello";
        rabbitTemplate.convertAndSend("delay.direct", "delay", msg,new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException{
                message.getMessageProperties().setDelay(5000);
                return message;
            }
        });
        log.info("消息发送成功");
    }
}
