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

package com.fsdm.it.fsdm_it_club.dto.request;

import com.fsdm.it.fsdm_it_club.entity.Event;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
public record EventCreationDto(
        String title,          // Title of the event
        String dateInterval,   // Date interval in range format
        String timeFrom,       // Start time
        String timeTo,         // End time
        String description,    // Description of the event
        List<String> topics,     // List of topics (selected)
        Boolean isOnline,      // Online or offline event
        String location,        // Event location (if offline)
        String onlinePlatform, // Online platform (if online)
        String onlineLink,     // URL for the online session
        boolean isTickerRequire,
        boolean isTicketAvailable,
        String image,          // Image URL
        Event.EventType type
) {

    public String title() {
        return title;
    }

    public LocalDate startDate() {
       String dateTrimmed = dateInterval.trim();
        if (dateTrimmed.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return LocalDate.parse(dateTrimmed);
        } else if (!dateTrimmed.matches("\\d{4}-\\d{2}-\\d{2} to \\d{4}-\\d{2}-\\d{2}")) {
            return null;
        }

        String[] dates = dateTrimmed.split(" to ");
        return LocalDate.parse(dates[0]);
    }

    public LocalDate endDate() {
        String dateTrimmed = dateInterval.trim();
        if (!dateTrimmed.matches("\\d{4}-\\d{2}-\\d{2} to \\d{4}-\\d{2}-\\d{2}")) {
            return startDate();
        }

        String[] dates = dateTrimmed.split(" to ");
        return LocalDate.parse(dates[1]);
    }

    public String timeFrom() {
        return timeFrom;
    }

    public LocalTime startTime() {
        return LocalTime.parse(timeFrom);
    }

    public LocalTime endTime() {
        return LocalTime.parse(timeTo);
    }

    public String timeTo() {
        return timeTo;
    }

    public String description() {
        return description;
    }

    public List<String> topics() {
        return topics;
    }

    public Boolean isOnline() {
        return isOnline;
    }

    public String location() {
        return location;
    }

    public String onlinePlatform() {
        return onlinePlatform;
    }

    public String onlineLink() {
        return onlineLink;
    }

}