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

package com.fsdm.it.fsdm_it_club.components;


import com.fsdm.it.fsdm_it_club.entity.JoinRequest;
import com.fsdm.it.fsdm_it_club.entity.User;
import com.fsdm.it.fsdm_it_club.events.JoinRequestCreatedEvent;
import com.fsdm.it.fsdm_it_club.services.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class JoinRequestEventListener {

    private final EmailService emailService;

    public JoinRequestEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void onJoinRequestCreated(JoinRequestCreatedEvent event) {
        JoinRequest joinRequest = event.getJoinRequest();
        System.out.printf(joinRequest.toString());
        // todo: implement the logic later
        User user = new User();
        user.setEmail("abdelhak.zaaim@usmba.ac.ma");
        user.setLName("FSDM IT CLUB Team");
        emailService.sendJoinRequestNotification(joinRequest, user);
        emailService.sendThankYouEmail(joinRequest);
    }
}
