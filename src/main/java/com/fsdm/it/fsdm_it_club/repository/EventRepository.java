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

package com.fsdm.it.fsdm_it_club.repository;

import com.fsdm.it.fsdm_it_club.entity.Event;
import com.fsdm.it.fsdm_it_club.entity.NewsletterEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByTitleContaining(String search, Pageable pageable);

    Page<Event> findByTitleContainingOrderByIdAsc(String title, Pageable pageable);
    Page<Event> findByTitleContainingOrderByIdDesc(String title, Pageable pageable);

    List<Event> findByStartDateTimeIsAfterOrderByStartDateTimeAsc(ZonedDateTime startDate);

    List<Event> findByStartDateTimeIsAfterOrderByStartDateTimeAsc(ZonedDateTime now, Pageable pageable);
}