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

package com.fsdm.it.fsdm_it_club.controllers;

import com.fsdm.it.fsdm_it_club.dto.request.ContactFormDto;
import com.fsdm.it.fsdm_it_club.dto.request.NewsLetterEmailDto;
import com.fsdm.it.fsdm_it_club.dto.response.MessageResponseDto;
import com.fsdm.it.fsdm_it_club.dto.response.EventResponseDto;
import com.fsdm.it.fsdm_it_club.entity.Contact;
import com.fsdm.it.fsdm_it_club.services.ContactService;
import com.fsdm.it.fsdm_it_club.services.EventService;
import com.fsdm.it.fsdm_it_club.services.NewsletterEmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller()
@Validated
public class HomeController {

    private final Validator validator;
    private final ContactService contactService;
    private final EventService eventService;

    private final NewsletterEmailService newsletterEmailService;

    public HomeController(Validator validator, ContactService contactService, EventService eventService, NewsletterEmailService newsletterEmailService) {
        this.validator = validator;
        this.contactService = contactService;
        this.eventService = eventService;
        this.newsletterEmailService = newsletterEmailService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<EventResponseDto> upComingEvents = eventService.getUpComingEvents(2).stream().map(event -> {
            return EventResponseDto.builder()
                    .id(event.getId())
                    .title(event.getTitle())
                    .description(event.getDescription())
                    .location(event.getLocation())
                    .startDate(DateTimeFormatter.ofPattern("MMMM yyyy").format(event.getStartDateTime()))

                    .endDate(event.getEndDateTime().format(DateTimeFormatter.ofPattern("MMMM yyyy")))
                    .startTime(event.getStartDateTime().format(DateTimeFormatter.ofPattern("hh:mm a")))
                    .startDay(event.getStartDateTime().getDayOfMonth())
                    .endTime(event.getEndDateTime().format(DateTimeFormatter.ofPattern("hh:mm a")))
                    .image(event.getImage())
                    .onlineLink(event.getOnlineLink())
                    .isTicketRequired(event.isTicketRequired())
                    .isTicketAvailable(event.isTicketAvailable())
                    .type(event.getType() != null ? event.getType().name() : null)
                    .isOnline(event.isOnline())
                    .where(event.isOnline()?event.getOnlineLink():event.getLocation())
                    .onlinePlatform(event.getOnlinePlatform())
                    .build();
        }).toList();


        System.out.printf("upComingEvents = %s", upComingEvents);
        model.addAttribute("upComingEvents", upComingEvents);
        return "home/index";
    }

    @GetMapping("/event-listing")
    public String eventListing() {
        return "home/event-listing";
    }



    @GetMapping("/join-us")
    public String joinUs() {
        return "home/join-us";
    }


    @PostMapping("/public/newsletter")
    public ResponseEntity<MessageResponseDto> newsletter(@RequestBody NewsLetterEmailDto newsLetterEmailDto) {
        try {
            newsletterEmailService.saveEmail(newsLetterEmailDto.email());
            return ResponseEntity.ok(MessageResponseDto.builder().message("Email added to newsletter").success(true).build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(MessageResponseDto.builder().message(e.getMessage()).success(false).build());
        }
    }

    @PostMapping("/contact")
    public ResponseEntity<MessageResponseDto> contact(@RequestBody ContactFormDto contactFormDto) {
        Contact contact = new Contact();
        contact.setEmail(contactFormDto.email());
        contact.setFName(contactFormDto.fName());
        contact.setMessage(contactFormDto.message());

        Contact sContact = contactService.saveContact(contact);

        return ResponseEntity.ok(MessageResponseDto.builder().message("Message sent").success(true).build());
    }

    // test
    @GetMapping("/login")
    public String login() {
        return "admin/sign-in";
    }

    @GetMapping("/team")
    public String team() {
        return "home/team";
    }
}
