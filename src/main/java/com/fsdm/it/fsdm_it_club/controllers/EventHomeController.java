/*
 * Copyright (c) 2025 FSDM IT Club.
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


import com.fsdm.it.fsdm_it_club.dto.response.EventResponseDto;
import com.fsdm.it.fsdm_it_club.entity.Event;
import com.fsdm.it.fsdm_it_club.services.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
public class EventHomeController {
    private final EventService eventService;

    public EventHomeController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/event/detail/{id}")
    public String eventDetail(@PathVariable("id") Long id, Model model) {
        Optional<Event> eventOptional = eventService.findById(id);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            EventResponseDto eventResponseDto = EventResponseDto.builder()
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
                    .where(event.isOnline() ? event.getOnlineLink() : event.getLocation())
                    .onlinePlatform(event.getOnlinePlatform())
                    .build();
            model.addAttribute("event", eventResponseDto);
        } else {
            return "redirect:/";
        }
        return "home/event-detail";
    }

}
