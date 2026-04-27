package com.ume.studentsystem.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void send(
         String to,
         String subject,
         String body
    ) {
        SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            mailSender.send(mail);
    }

    public void sendWithAttachment(
            String to,
            String subject,
            String body,
            byte[] file,
            String fileName
    ) {
        try {
            MimeMessage message =
                    mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            helper.addAttachment(
                    fileName,
                    new ByteArrayResource(file)
            );

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Email send failed",
                    e
            );
        }
    }
}
