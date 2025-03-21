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

package com.fsdm.it.fsdm_it_club.entity;

import com.fsdm.it.fsdm_it_club.converters.ListStringToStringConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String fName;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String email;

    @Convert(converter = ListStringToStringConverter.class)
    java.util.List<String> topicsOfInterest;
    @Convert(converter = ListStringToStringConverter.class)
    List<String> cellsOfInterest;



    @Column(nullable = false)
    private String degree;

    private String major;

    private String message;
    private LocalDateTime createdAt;
    private String status;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        status = Status.PENDING.name();
    }

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

}
