package com.mountblue.app.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String body)
    {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("123dheerajkumarsharma@gmail.com");
        simpleMailMessage.setTo("123dheerajkumarsharma@gmail.com");
        simpleMailMessage.setSubject("Feedback for Calendly");
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);

    }

    public void sendNotification(String body,String...to) {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("123dheerajkumarsharma@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("Appointment Confirmation :From Calendly");
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
    }
}
