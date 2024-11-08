package com.mengdl.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfiguration {
    @Bean
    public FanoutExchange fanoutExchange(){
        // ExchangeBuilder.fanoutExchange(null).durable(true).build();
        return new FanoutExchange("hmall.fanout2");
    }

    @Bean
    public Queue fanoutQueue3(){
        // QueueBuilder.durable("fanout.queue3").build();
        return new Queue("fanout.queue3");
    }

    @Bean
    public Binding bindFanoutExchangeAndQueue3(Queue fanoutQueue3, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue3).to(fanoutExchange);
    }
}
