package com.abdullah.repository.rabbitmq.consumer;

import com.abdullah.repository.rabbitmq.model.RegisterElasticModel;

import com.abdullah.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j//console a log infosu vermek icin bunu kullandik :))
public class RegisterConsumer {

    private final UserProfileService userProfileService;



    @RabbitListener(queues = "${rabbitmq.queueregisterelastic}")
    public void newUserCreate(RegisterElasticModel model){
        log.info("User {}",model.toString());
        userProfileService.createUserWithRabbitMq(model);

    }
}
