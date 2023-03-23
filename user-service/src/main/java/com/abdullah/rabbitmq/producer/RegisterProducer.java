package com.abdullah.rabbitmq.producer;

import com.abdullah.rabbitmq.model.RegisterElasticModel;
import com.abdullah.rabbitmq.model.RegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterProducer {


    @Value("${rabbitmq.exchange-user}")
    private String directExchange;
    @Value("${rabbitmq.elasticregisterKey}")
    private String registerBindingKey;

    private final RabbitTemplate rabbitTemplate;



    public void sendNewUser(RegisterElasticModel model){
        rabbitTemplate.convertAndSend(directExchange,registerBindingKey,model);
    }
}
