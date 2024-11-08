package com.mengdl.publisher.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MqReturnConfig implements ApplicationContextAware{
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        // 配置回调
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned){
                log.debug("消息发送失败: exchange:{}, route:{}, replyCode:{}, replyText:{}, message:{}", 
                returned.getExchange(), 
                returned.getRoutingKey(), 
                returned.getReplyText(),
                returned.getReplyCode(),
                returned.getMessage());
            }
        });
    }

}
