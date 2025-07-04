package com.auction.springrestapi;

import com.auction.springrestapi.Service.MailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailSenderServiceTests {

    @Autowired
    private MailSenderService mailSenderService;

    @Test
    void testSendRealEmail() {
        String to = "jhifdfnkjf@gmail.com";
        String subject = "Test Email from Integration Test";
        String body = "This is a real email sent from Spring Boot test.";

        mailSenderService.sendMail(to, subject, body);

        // No assertion needed – just check your inbox!
    }
}
