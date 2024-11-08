package com.mengdl.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
public class DirectConfiguration {
    @Bean
    public DirectExchange directExchange(){
        // ExchangeBuilder.directExchange(null).durable(true).build();
        return new DirectExchange("hmall.direct");
    }

    @Bean
    public Queue directQueue1(){
        // QueueBuilder.durable("direct.queue1").build();
        return new Queue("direct.queue1");
    }

    @Bean
    public Binding bindDirectExchangeAndQueue1Red(Queue directQueue1, DirectExchange directExchange){
        return BindingBuilder.bind(directQueue1).to(directExchange).with("red");
    }

    @Bean
    public Binding bindDirectExchangeAndQueue1Blue(Queue directQueue1, DirectExchange directExchange){
        return BindingBuilder.bind(directQueue1).to(directExchange).with("blue");
    }

    @Bean
    public Queue directQueue2(){
        // QueueBuilder.durable("direct.queue1").build();
        return new Queue("direct.queue2");
    }

    @Bean
    public Binding bindDirectExchangeAndQueue2Red(Queue directQueue2, DirectExchange directExchange){
        return BindingBuilder.bind(directQueue2).to(directExchange).with("red");
    }

    @Bean
    public Binding bindDirectExchangeAndQueue2Yellow(Queue directQueue2, DirectExchange directExchange){
        return BindingBuilder.bind(directQueue2).to(directExchange).with("yellow");
    }
}
