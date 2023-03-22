package com.abdullah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class MailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailServiceApplication.class);
    }
  /*  private final JavaMailSender javaMailSender;
    public MailServiceApplication(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender; deneme amaclıydı burası
    }
    @EventListener(ApplicationReadyEvent.class)
    public void sendMail(){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("${java6mailusername}");
        mailMessage.setTo("asdasdasd@gmail.com");//asdasdasdas@gmail.com
        mailMessage.setSubject("Aktivasyon kodunuz....:");
        mailMessage.setText("asd87");
        javaMailSender.send(mailMessage);
    }*/
}