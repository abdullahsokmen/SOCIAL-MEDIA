package com.abdullah.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {


    @Value("${rabbitmq.queueregisterelastic}")
    private String elasticRegisterQueue;

    @Value("${rabbitmq.elasticregisterKey}")
    private String elasticRegisterBindingKey;

    @Value("${rabbitmq.exchange-user}")
    private String exchange;



    @Bean
    Queue registerQueueElastic(){
        return new Queue(elasticRegisterQueue);
    }





}
