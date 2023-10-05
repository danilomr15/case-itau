package br.com.danilomr.notificacao.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${email.sender}")
    private String sender;

    @Value("${email.subject}")
    private String subject;

    private final JavaMailSender emailSender;

    public void sendEmail(final String recipientEmail,
                          final String message) {

        final var email = new SimpleMailMessage();
        email.setTo(recipientEmail);
        email.setSubject(this.subject);
        email.setFrom(this.sender);
        email.setText(message);
        emailSender.send(email);
    }

}
