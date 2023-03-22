package com.abdullah.service;

import com.abdullah.rabbitmq.model.RegisterMailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender javaMailSender;


    public void sendMail(RegisterMailModel model){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("${java6mailusername}");
        mailMessage.setTo(model.getEmail());//
        mailMessage.setSubject("Aktivasyon kodunuz....:");
        mailMessage.setText(model.getUsername()+" adıyla başarılı bir şekilde kayıt oldunuz");
        mailMessage.setText("Aktivasyon kodunuz....:"+model.getActivationCode());
        javaMailSender.send(mailMessage);
    }


}
