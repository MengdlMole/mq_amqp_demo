package com.mengdl.publisher;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PublisherApplication {
    public static void main(String[] args) {
        SpringApplication.run(PublisherApplication.class, args);
    }

    // @Bean
    // public MessageConverter jacksonMessageConverter(){
    //     return new Jackson2JsonMessageConverter();
    // }

    @Bean
    public MessageConverter jacksonMessageConverter(){
        // 1. 定义消息转换器
        Jackson2JsonMessageConverter mJ2JMessageConverter = new Jackson2JsonMessageConverter();
        // 2. 设置消息转换器开启创建消息Id，用于识别消息，也可以在业务中基于ID实现消息去重
        mJ2JMessageConverter.setCreateMessageIds(true);
        return mJ2JMessageConverter;
    }
}
