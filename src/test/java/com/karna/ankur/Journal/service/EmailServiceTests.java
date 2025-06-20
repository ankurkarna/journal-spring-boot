package com.karna.ankur.Journal.service;

import com.karna.ankur.Journal.Service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    EmailService emailService;

    @Test
    void testEmail(){
        emailService.sendEmail("faizahmed2696@gmail.com","testing automated java mail", "Hi user");
    }
}
