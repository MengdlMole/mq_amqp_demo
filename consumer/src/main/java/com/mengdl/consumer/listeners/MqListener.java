package com.mengdl.consumer.listeners;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqListener {
    // @RabbitListener(queues = "simple.queue")
    // public void listenSimpleQueue(String msg) throws InterruptedException{
    //     log.info("listenSimpleQueue received message: {}", msg);
    //     // throw new InterruptedException();
    // }

    // @RabbitListener(queuesToDeclare = @Queue(
    //     name = "simple.queue",
    //     durable = "true",
    //     arguments = {
    //         @Argument(name = "x-message-ttl", value = "10000"),
    //         @Argument(name = "x-dead-letter-exchange", value = "xdl.direct"),
    //         @Argument(name = "x-dead-letter-routing-key", value = "xdl")
    //     }
    // ))
    // public void listenSimpleQueue(String msg) throws InterruptedException{
    //     log.info("listenSimpleQueue received message: {}", msg);
    //     // throw new InterruptedException();
    // }

    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(name = "dlx.queue", durable = "true"),
        exchange = @Exchange(name = "dlx.direct", type = ExchangeTypes.DIRECT, durable = "true"),
        key = "dlx"
    ))
    public void listenDlxQueue(String msg) throws InterruptedException{
        log.info("listenDlxQueue received message: {}", msg);
    }

    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue1(String msg) throws InterruptedException{
        log.info("listenWorkQueue1 received message: {}", msg);
        Thread.sleep(20);
    }

    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue2(String msg) throws InterruptedException{   
        log.info("listenWorkQueue2 received message: {}", msg);
        Thread.sleep(200);
    }

    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String msg) throws InterruptedException{
        log.info("listenFanoutQueue1 received message: {}", msg);
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String msg) throws InterruptedException{   
        log.info("listenFanoutQueue2 received message: {}", msg);
    }

    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(name = "direct.queue1", durable = "true"),
        exchange = @Exchange(name = "hmall.direct", type = ExchangeTypes.DIRECT, durable = "true"),
        key = {"red", "blue"}
    ))
    public void listenDirectQueue1(String msg) throws InterruptedException{
        log.info("listenDirectQueue1 received message: {}", msg);
    }

    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(name = "direct.queue2", durable = "true"),
        exchange = @Exchange(name = "hmall.direcst", type = ExchangeTypes.DIRECT, durable = "true"),
        key = {"red", "yellow"}
    ))
    public void listenDirectQueue2(String msg) throws InterruptedException{   
        log.info("listenDirectQueue2 received message: {}", msg);
    }

    @RabbitListener(queues = "topic.queue1")
    public void listenTopicQueue1(String msg) throws InterruptedException{
        log.info("listenTopicQueue1 received message: {}", msg);
    }

    @RabbitListener(queues = "topic.queue2")
    public void listenTopicQueue2(String msg) throws InterruptedException{   
        log.info("listenTopicQueue2 received message: {}", msg);
    }

    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(name = "object.queue", durable = "true"),
        exchange = @Exchange(name = "hmall.object", type = ExchangeTypes.DIRECT, durable = "true"),
        key = "object"
    ))
    public void listenObjectQueue(Map<String, Object> msg) throws InterruptedException{
        log.info("listenObjectQueue received message: {}", msg);
    }

    @RabbitListener(queuesToDeclare = @Queue(
        name = "lazy.queue",
        durable = "true",
        arguments = @Argument(name = "x-queue-mode", value = "lazy")
    ))
    public void listenLazyQueue(String msg) throws InterruptedException{
        log.info("listenLazyQueue received message: {}", msg);
    }

    // @RabbitListener(bindings = @QueueBinding(
    //     value = @Queue(name = "error.queue", durable = "true", arguments = {
    //         @Argument(name = "x-queue-mode", value = "lazy"),
    //         @Argument(name = "dead-letter-exchange", value = "error.direct"),
    //         @Argument(name = "dead-letter-routing-key", value = "error")
    //     }),
    //     exchange = @Exchange(name = "error.direct", type = ExchangeTypes.DIRECT, durable = "true"),
    //     key = "error"
    // ))
    // public void listenErrorQueue(String msg) throws InterruptedException{
    //     log.info("listenErrorQueue received message: {}", msg);
    // }

    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(name = "delay.queue", durable = "true"),
        exchange = @Exchange(name = "delay.direct", delayed = "true", type = ExchangeTypes.DIRECT, durable = "true"),
        key = "delay"
    ))
    public void listenDelayQueue(String msg) throws InterruptedException{
        log.info("listenDelayQueue received message: {}", msg);
    }
}
