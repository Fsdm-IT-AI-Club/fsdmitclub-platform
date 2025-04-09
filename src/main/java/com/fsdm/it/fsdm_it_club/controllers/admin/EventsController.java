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

package com.fsdm.it.fsdm_it_club.controllers.admin;


import com.fsdm.it.fsdm_it_club.dto.request.EventCreationDto;
import com.fsdm.it.fsdm_it_club.dto.request.PaginateTableRequestDto;
import com.fsdm.it.fsdm_it_club.dto.response.*;
import com.fsdm.it.fsdm_it_club.entity.Event;
import com.fsdm.it.fsdm_it_club.services.EventService;
import com.fsdm.it.fsdm_it_club.util.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class EventsController {
    private final EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/admin/events/add")
    public String addEvent(Model model) {
        Event.EventType[] eventTypes = Event.EventType.values();

        model.addAttribute("eventTypes", eventTypes);
        return "admin/events/add";
    }

    @GetMapping("/admin/events/view/{id}")
    public String viewEvent(@PathVariable("id") Long id, Model model) {

        Optional<Event> eventOptional = eventService.findById(id);

        if (eventOptional.isEmpty()) {
            return "redirect:/admin/events/list";
        }

        Event.EventType[] eventTypes = Event.EventType.values();

        model.addAttribute("eventTypes", eventTypes);

        Event event = eventOptional.get();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedStartTime = event.getStartDateTime().format(formatter);
        String formattedEndTime = event.getEndDateTime().format(formatter);



        // formated dateInterval
        StringBuilder formatedDateInterval = new StringBuilder(event.getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        if (event.getEndDateTime() != null) {
            formatedDateInterval.append(" to ").append(event.getEndDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        // Add formatted values to the model
        model.addAttribute("formattedStartTime", formattedStartTime);
        model.addAttribute("formattedEndTime", formattedEndTime);


        model.addAttribute("formattedDateInterval", formatedDateInterval.toString());

        model.addAttribute("event", event);
        return "admin/events/view";
    }


    @GetMapping("/admin/events/edit/{id}")
    public String addEvent(@PathVariable("id") Long id, Model model) {

        Optional<Event> eventOptional = eventService.findById(id);

        if (eventOptional.isEmpty()) {
            return "redirect:/admin/events/list";
        }

        Event.EventType[] eventTypes = Event.EventType.values();

        model.addAttribute("eventTypes", eventTypes);

        Event event = eventOptional.get();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedStartTime = event.getStartDateTime().format(formatter);
        String formattedEndTime = event.getEndDateTime().format(formatter);



        // formated dateInterval
        StringBuilder formatedDateInterval = new StringBuilder(event.getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        if (event.getEndDateTime() != null) {
            formatedDateInterval.append(" to ").append(event.getEndDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        // Add formatted values to the model
        model.addAttribute("formattedStartTime", formattedStartTime);
        model.addAttribute("formattedEndTime", formattedEndTime);


        model.addAttribute("formattedDateInterval", formatedDateInterval.toString());

        model.addAttribute("event", event);
        return "admin/events/edit";
    }


    @PostMapping("/admin/events/edit/{id}")
    public ResponseEntity<MessageResponseDto> editEvent(@PathVariable("id") Long id, @RequestBody EventCreationDto eventCreationDTO) {
        Optional<Event> eventOptional = eventService.findById(id);

        if (eventOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(MessageResponseDto.builder().message("Event not found").success(false).build());
        }

        Event event = eventOptional.get();

        event.setTitle(eventCreationDTO.title());

        ZonedDateTime startDateTime = eventCreationDTO.startDate().atTime(eventCreationDTO.startTime()).atZone(ZoneId.of(Constants.DEFAULT_TIME_ZONE));
        event.setStartDateTime(startDateTime);

        ZonedDateTime endDateTime = eventCreationDTO.endDate().atTime(eventCreationDTO.endTime()).atZone(ZoneId.of(Constants.DEFAULT_TIME_ZONE));
        event.setEndDateTime(endDateTime);

        event.setDescription(eventCreationDTO.description());
        event.setTopics(eventCreationDTO.topics());

        event.setOnline(eventCreationDTO.isOnline());

        event.setOnlinePlatform(eventCreationDTO.onlinePlatform());

        if (eventCreationDTO.isOnline()) {
            event.setLocation(null);
            event.setOnlineLink(eventCreationDTO.onlineLink());
        } else if (eventCreationDTO.location() == null || eventCreationDTO.location().isEmpty()) {
            return ResponseEntity.badRequest().body(MessageResponseDto.builder().message("Location is required").success(false).build());
        } else {
            event.setLocation(eventCreationDTO.location());
        }

        event.setImage(eventCreationDTO.image());
        event.setTicketRequired(eventCreationDTO.isTickerRequire());
        event.setTicketAvailable(eventCreationDTO.isTicketAvailable());
        event.setType(eventCreationDTO.type());
        eventService.saveEvent(event);
        return ResponseEntity.ok(MessageResponseDto.builder().message("Event updated successfully").success(true).build());
    }

    @GetMapping("/admin/events/list")
    public String eventsList() {
        return "admin/events/list";
    }

    @GetMapping("/admin/events/delete/{id}")
    public ResponseEntity<?> eventDelete(@PathVariable Long id) {
        Optional<Event> eventOptional = eventService.findById(id);
        if (eventOptional.isPresent()) {
            eventService.deleteById(id);
            return ResponseEntity.ok(MessageResponseDto.builder().message("Event deleted successfully").success(true).build());
        } else {
            return ResponseEntity.badRequest().body(MessageResponseDto.builder().message("Event not found").success(false).build());
        }
    }

    @PostMapping("/admin/events/list")
    @ResponseBody
    public ResponseEntity<?> eventsList(@RequestBody PaginateTableRequestDto requestDto) {
        int page = requestDto.page();
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, requestDto.length());
        Page<Event> eventsList = eventService.searchByTitle(requestDto.search(), requestDto.order(), pageable);

        Page<EventListItemDto> emailsPageDto = eventsList.map(event -> {

            String dateInterval = event.getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (event.getEndDateTime() != null) {
                dateInterval += " to " + event.getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }

            String timeInterval = event.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " to " + event.getEndDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));

            boolean isPast = false;
            if (event.getEndDateTime() != null) {
                LocalDate now = LocalDate.now(ZoneId.of(Constants.DEFAULT_TIME_ZONE));
                LocalDate endDate = event.getEndDateTime().toLocalDate();
                isPast = now.isAfter(endDate);
            } else {
                isPast = event.getStartDateTime().isBefore(ZonedDateTime.now(ZoneId.of(Constants.DEFAULT_TIME_ZONE)));
            }


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

        event.setTitle(eventCreationDTO.title());


        ZonedDateTime startDateTime = eventCreationDTO.startDate().atTime(eventCreationDTO.startTime()).atZone(ZoneId.of(Constants.DEFAULT_TIME_ZONE));
        event.setStartDateTime(startDateTime);

        ZonedDateTime endDateTime = eventCreationDTO.endDate().atTime(eventCreationDTO.endTime()).atZone(ZoneId.of(Constants.DEFAULT_TIME_ZONE));
        event.setEndDateTime(endDateTime);

        event.setDescription(eventCreationDTO.description());
        event.setTopics(eventCreationDTO.topics());

        event.setOnline(eventCreationDTO.isOnline());

        event.setOnlinePlatform(eventCreationDTO.onlinePlatform());

        if (eventCreationDTO.isOnline()) {
            event.setLocation(null);
            event.setOnlineLink(eventCreationDTO.onlineLink());
        } else if (eventCreationDTO.location() == null || eventCreationDTO.location().isEmpty()) {
            return ResponseEntity.badRequest().body(MessageResponseDto.builder().message("Location is required").success(false).build());
        } else {
            event.setLocation(eventCreationDTO.location());
        }

        event.setImage(eventCreationDTO.image());
        event.setTicketRequired(eventCreationDTO.isTickerRequire());
        event.setTicketAvailable(eventCreationDTO.isTicketAvailable());
        event.setType(eventCreationDTO.type());


        eventService.saveEvent(event);

        return ResponseEntity.ok(MessageResponseDto.builder().message("Event added successfully").success(true).build());
    }

    @GetMapping("/admin/events/calendar")
    public ResponseEntity<?> eventsCalendar() {
        ZonedDateTime startDate = ZonedDateTime.now(ZoneId.of(Constants.DEFAULT_TIME_ZONE)).minusDays(15);
        List<Event> events = eventService.getEventsFromStartDate(startDate);


        List<EventCalendarItemDto> eventsDto = events.stream().map(event -> EventCalendarItemDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .start(event.getStartDateTime().toString())
                .end(event.getEndDateTime().toString())
                .url("/admin/events/view/" + event.getId())
                .description(event.getDescription())
                .backgroundColor(event.isPast() ? "#4C585B" : "#5CB338")
                .build()).collect(Collectors.toList());

        EventCalendarResponseDto response = EventCalendarResponseDto.builder()
                .initialDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .events(eventsDto)
                .build();

        return ResponseEntity.ok(response);
    }
}
