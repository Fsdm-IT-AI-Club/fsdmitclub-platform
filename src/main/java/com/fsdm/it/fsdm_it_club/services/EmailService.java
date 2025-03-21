/*
 * Copyright (c) 2024, 2025 FSDM IT Club.
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


import com.fsdm.it.fsdm_it_club.entity.Contact;
import com.fsdm.it.fsdm_it_club.entity.JoinRequest;
import com.fsdm.it.fsdm_it_club.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static com.fsdm.it.fsdm_it_club.helper.Constants.CLUB_NAME;
import static com.fsdm.it.fsdm_it_club.helper.Constants.MAIN_URL;

@Service
public class EmailService {
    private static final String JOIN_REQUEST_NOTIFE_TEMPLATE_NAME = "email/join_request/admin_notif";
    private static final String CONTTACT_NOTIFE_TEMPLATE_NAME = "email/contact/contact";
    private static final String MESAGET_NOTIFE_TEMPLATE_NAME = "email/message";
    @Value("${spring.mail.username}")
    private String clubEmail;


    private final TemplateEngine templateEngine;
    private final JavaMailSender emailSender;

    public EmailService(TemplateEngine templateEngine, JavaMailSender emailSender) {
        this.templateEngine = templateEngine;
        this.emailSender = emailSender;
    }

    public void sendJoinRequestNotification(JoinRequest joinRequest, User toUser) {
        if (joinRequest == null) {
            System.err.println("JoinRequest object is null. Aborting email notification.");
            return;
        }

        Context context = getJoinRequestContext(joinRequest, toUser);
        try {
            String body = templateEngine.process(JOIN_REQUEST_NOTIFE_TEMPLATE_NAME, context);

            sendEmail(toUser.getEmail(), "New Join Request - "+ joinRequest.getFName(), body);
        } catch (Exception e) {
            System.out.println("Error occurred while sending email with template " + e.getMessage());
        }
        sendThankYouEmail(joinRequest);
    }

    private static Context getJoinRequestContext(JoinRequest joinRequest, User toUser) {
        Map<String, Object> variables = Map.of(
                "toLastName", toUser.getLName(),
                "fName", joinRequest.getFName(),
                "email", joinRequest.getEmail(),
                "phone", joinRequest.getPhone(),
                "degree", joinRequest.getDegree(),
                "topicsOfInterest", joinRequest.getTopicsOfInterest(),
                "cellsOfInterest", joinRequest.getCellsOfInterest(),
                "major", joinRequest.getMajor(),
                "message", joinRequest.getMessage(),
                "adminDashboardUrl", MAIN_URL + "/admin/login"
        );

        Context context = new Context();
        context.setVariables(variables);
        return context;
    }

    public void sendContactNotification(Contact contact, User toUser) {
        if (contact == null) {
            System.err.println("Contact object is null. Aborting email notification.");
            return;
        }

        Context context = getContactContext(contact, toUser);
        try {
            String body = templateEngine.process(CONTTACT_NOTIFE_TEMPLATE_NAME, context);

            sendEmail(toUser.getEmail(), "New Contact Message", body);
        } catch (Exception e) {
            System.out.println("Error occurred while sending email with template " + e.getMessage());
        }
    }

    private static Context getContactContext(Contact contact, User toUser) {
        Map<String, Object> variables = Map.of(
                "toLastName", toUser.getLName(),
                "fName", contact.getFName(),
                "email", contact.getEmail(),
                "message", contact.getMessage(),
                "adminDashboardUrl", MAIN_URL + "/admin/login"
        );

        Context context = new Context();
        context.setVariables(variables);
        return context;
    }


    @Async("taskExecutor")
    protected void sendEmail(String to, String subject, String body) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(clubEmail, CLUB_NAME);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        emailSender.send(message);
    }

    public void sendThankYouEmail(JoinRequest joinRequest) {
        if (joinRequest == null) {
            System.err.println("JoinRequest object is null. Aborting email notification.");
            return;
        }

        Context context = new Context();
        context.setVariable("fName", joinRequest.getFName());
    context.setVariable("message", "Thank you for your interest in joining the FSDM IT Club. Your request has been successfully registered. One of our team members will reach out to you for the next steps.");
    //    context.setVariable("message", "ff");


        try {
            String body = templateEngine.process(MESAGET_NOTIFE_TEMPLATE_NAME, context);
            sendEmail(joinRequest.getEmail(), "Join Request Received", body);
        } catch (Exception e) {
            System.out.println("Error occurred while sending thank you email: " + e.getMessage());
        }
    }
}
