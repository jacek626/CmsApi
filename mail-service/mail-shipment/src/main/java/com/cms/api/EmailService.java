package com.cms.api;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {

    public void sendEmail() {
        //provide recipient's email ID
        String to = "jacek626@gmail.com";
        //provide sender's email ID
        String from = "jakartafrom@example.com";
        //provide Mailtrap's username
        final String username = "7658d23872894e";
        //provide Mailtrap's password
        final String password = "360f9d98330228";
        //provide Mailtrap's host address
        String host = "smtp.mailtrap.io";
        //configure Mailtrap's SMTP server details
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        //create the Session object
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            //create a MimeMessage object
            Message message = new MimeMessage(session);
            //set From email field
            message.setFrom(new InternetAddress(from));
            //set To email field
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //set email subject field
            message.setSubject("Here comes Jakarta Mail!");
            //set the content of the email message
            message.setText("Just discovered that Jakarta Mail is fun and easy to use");
            //send the email message
            Transport.send(message);
            System.out.println("Email Message Sent Successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
