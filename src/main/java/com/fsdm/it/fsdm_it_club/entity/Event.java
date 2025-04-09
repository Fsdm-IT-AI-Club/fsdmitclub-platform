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

package com.fsdm.it.fsdm_it_club.entity;

import com.fsdm.it.fsdm_it_club.converters.ListStringToStringConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String title;

    private String description;
    private String location;

    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;

    private String image;
    private String category;
    private String status;

    @Enumerated(EnumType.STRING)
    private EventType type;

    private boolean isFeatured;
    private boolean isPublished;
    private boolean isOnline;

    private String onlinePlatform;
    private String onlineLink;

    @Column(nullable = false)
    private boolean isTicketRequired = false;
    @Column(nullable = false)
    private boolean isTicketAvailable = false;

    private int views;

    @Convert(converter = ListStringToStringConverter.class)
    private List<String> topics;

    @Convert(converter = ListStringToStringConverter.class)
    private List<String> keyWords;

    public boolean isPast() {
        if (endDateTime != null) {
            return endDateTime.isBefore(ZonedDateTime.now());
        } else {
            return startDateTime.isBefore(ZonedDateTime.now());
        }
    }

    public enum EventType {
        WORKSHOP("Workshop"),
        TRAINING("Training"),
        SEMINAR("Seminar"),
        WEBINAR("Webinar"),
        MEETUP("Meetup"),
        CONFERENCE("Conference"),
        HACKATHON("Hackathon"),
        COMPETITION("Competition"),
        OTHER("Other");

        private final String type;

        EventType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

}
