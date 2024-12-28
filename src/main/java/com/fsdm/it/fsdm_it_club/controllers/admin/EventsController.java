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

package com.fsdm.it.fsdm_it_club.controllers.admin;


import com.fsdm.it.fsdm_it_club.dto.request.EventCreationDto;
import com.fsdm.it.fsdm_it_club.dto.request.PaginateTableRequestDto;
import com.fsdm.it.fsdm_it_club.dto.response.EventListItemDto;
import com.fsdm.it.fsdm_it_club.dto.response.MessageResponseDto;
import com.fsdm.it.fsdm_it_club.dto.response.NewsLetterEmailsResponseDto;
import com.fsdm.it.fsdm_it_club.entity.Event;
import com.fsdm.it.fsdm_it_club.services.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
public class EventsController {
    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/admin/events/add")
    public String addEvent() {
        return "admin/events/add";
    }

    @GetMapping("/admin/events/list")
    public String eventsList() {
        return "admin/events/list";
    }

    @GetMapping("/admin/events/delete/{id}")
    public ResponseEntity<?> eventDelete(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/admin/events/list")
    public ResponseEntity<?> eventsList(@RequestBody PaginateTableRequestDto requestDto) {
        int page = requestDto.page();
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, requestDto.length());
        Page<Event> eventsList = eventService.searchByTitle(requestDto.search(), requestDto.order(), pageable);

        Page<EventListItemDto> emailsPageDto = eventsList.map(event -> {

            String dateInterval = event.getStartDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + " to " + event.getEndDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String timeInterval = event.getStartTime() + " to " + event.getEndTime();

            boolean isPast = event.getEndDate().isBefore(java.time.LocalDate.now()) || (event.getEndDate().isEqual(java.time.LocalDate.now()) && event.getEndTime().compareTo(java.time.LocalTime.now()) < 0);
            return new EventListItemDto(event.getId(),
                    event.getTitle(),
                    dateInterval,
                    timeInterval,
                    event.isOnline(),
                    isPast
            );
        });

        NewsLetterEmailsResponseDto response = NewsLetterEmailsResponseDto.builder()
                .draw(requestDto.draw())
                .recordsTotal(emailsPageDto.getTotalElements())
                .recordsFiltered(emailsPageDto.getTotalElements())
                .data(emailsPageDto.getContent())
                .build();


        return ResponseEntity.ok(response);
    }


    @PostMapping("/admin/events/add")
    public ResponseEntity<MessageResponseDto> addEvent(@RequestBody EventCreationDto eventCreationDTO) {
        Event event = new Event();
        event.setStartDate(eventCreationDTO.startDate());

        event.setTitle(eventCreationDTO.title());

        event.setStartDate(eventCreationDTO.startDate());
        event.setEndDate(eventCreationDTO.endDate());

        event.setStartTime(eventCreationDTO.startTime());
        event.setEndTime(eventCreationDTO.endTime());

        event.setDescription(eventCreationDTO.description());
        event.setTopics(eventCreationDTO.topics());

        event.setOnline(eventCreationDTO.isOnline());

        event.setOnlinePlatform(eventCreationDTO.onlinePlatform());
        event.setOnlineLink(eventCreationDTO.onlineLink());


        event.setLocation(eventCreationDTO.location());

        eventService.saveEvent(event);

        return ResponseEntity.ok(MessageResponseDto.builder().message("Event added successfully").success(true).build());
    }
}