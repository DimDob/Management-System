package com.example.RabbitMQ.Consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Component
@Slf4j
public class EmailSender {


    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    private void send(String to, String eventSubject, String eventContent) {

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", 587);
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(username, password);
            }
        });

        try {

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(eventSubject);

            message.setContent(eventContent, "text/html; charset=utf-8");

            Transport.send(message);

        } catch (MessagingException exc) {

            throw new RuntimeException(exc);
        }
    }

    public void sendMail(String receiver, String subject, String content) {
        this.send(receiver, subject, content);
    }
}
