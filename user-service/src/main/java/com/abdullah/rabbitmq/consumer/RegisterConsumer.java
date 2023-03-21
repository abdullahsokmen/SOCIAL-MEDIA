package com.abdullah.rabbitmq.consumer;

import com.abdullah.mapper.IUserMapper;
import com.abdullah.rabbitmq.model.RegisterModel;
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



    @RabbitListener(queues = "register-queue")
    public void newUserCreate(RegisterModel model){
        log.info("User {}",model.toString());
        userProfileService.createUserWithRabbitMq(model);
        //userProfileService.createUser(IUserMapper.INSTANCE.toNewCreateUserRequestDto(model));2. yontem :)
    }
}
