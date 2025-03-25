package com.example.users.services;
import com.resend.*;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${RESEND_KEY}")
    private String SECRET_KEY;
    @Value("${EMAIL}")
    private String EMAIL_FROM;

    private Resend resend = new Resend(SECRET_KEY);

    public void sendEmail(String to, String subject, String body) {
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(EMAIL_FROM)
                .to(to)
                .subject(subject)
                .html(body)
                .build();

        try {
            CreateEmailResponse data = resend.emails().send(params);
            System.out.println(data);
        } catch (ResendException e) {
            e.printStackTrace();
        }
    }

}
