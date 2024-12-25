/*
 * Copyright (c) 2024 FSDM IT Club.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fsdm.it.fsdm_it_club.services;


import com.fsdm.it.fsdm_it_club.entity.JoinRequest;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class EmailService{
    private static final String JOIN_REQUEST_NOTIFE_TEMPLATE_NAME = "email/join_request/admin_notif";


    private final TemplateEngine templateEngine;
    private final JavaMailSender emailSender;

    public EmailService(TemplateEngine templateEngine, JavaMailSender emailSender) {
        this.templateEngine = templateEngine;
        this.emailSender = emailSender;
    }
    @Async("taskExecutor")
    public void sendAdminNotification(JoinRequest joinRequest) {
        if (joinRequest == null) {
            // Log the error and exit the method to prevent further issues.
            System.err.println("JoinRequest object is null. Aborting email notification.");
            return;
        }

        Map<String, Object> variables = Map.of(
                "adminName", "Admin",
                "fName", joinRequest.getFName(),
                "email", joinRequest.getEmail(),
                "phone", joinRequest.getPhone(),
                "degreeAndMajor", joinRequest.getDegreeAndMajor(),
                "message", joinRequest.getMessage(),
                "adminDashboardUrl", "http://localhost:8080/admin/dashboard"
        );

        Context context = new Context();
        context.setVariables(variables);
        try {
            String body = templateEngine.process(JOIN_REQUEST_NOTIFE_TEMPLATE_NAME, context);

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("contact@fsdmitclub.com","FSDM IT Club");
            helper.setTo("abdelhakzammii@gmail.com");
            helper.setSubject("New Join Request");
            helper.setText(body, true);

            emailSender.send(message);
        } catch (Exception e) {
            System.out.println("Error occurred while sending email with template " + e.getMessage());
        }
    }
}
