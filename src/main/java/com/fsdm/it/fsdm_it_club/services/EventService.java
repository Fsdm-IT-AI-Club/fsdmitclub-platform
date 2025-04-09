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


import com.fsdm.it.fsdm_it_club.entity.Event;
import com.fsdm.it.fsdm_it_club.model.TableSortOrder;
import com.fsdm.it.fsdm_it_club.model.enums.SortOrder;
import com.fsdm.it.fsdm_it_club.repository.EventRepository;
import com.fsdm.it.fsdm_it_club.util.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public Page<Event> searchByTitle(String search, List<TableSortOrder> order, Pageable pageable) {

        if (order.isEmpty()) {
            return eventRepository.findByTitleContaining(search, pageable);
        }

        TableSortOrder sortOrder = order.get(0);

        SortOrder direction = sortOrder.dir();

        if (direction == SortOrder.ASC) {
            return eventRepository.findByTitleContainingOrderByIdAsc(search, pageable);
        } else {
            return eventRepository.findByTitleContainingOrderByIdDesc(search, pageable);
        }


    }

    public Page<Event> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getEventsFromStartDate(ZonedDateTime startDate) {
        return eventRepository.findByStartDateTimeIsAfterOrderByStartDateTimeAsc(startDate);
    }

    public List<Event> getUpComingEvents(int number) {
        return eventRepository.findByStartDateTimeIsAfterOrderByStartDateTimeAsc(ZonedDateTime.now(ZoneId.of(Constants.DEFAULT_TIME_ZONE)), PageRequest.of(0, number));
    }

}
