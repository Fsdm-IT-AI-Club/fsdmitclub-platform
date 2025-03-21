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


import com.fsdm.it.fsdm_it_club.dto.request.JoinRequestDto;
import com.fsdm.it.fsdm_it_club.dto.response.MessageResponseDto;
import com.fsdm.it.fsdm_it_club.entity.JoinRequest;
import com.fsdm.it.fsdm_it_club.services.JoinRequestService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class JoinRequestController {
    private final JoinRequestService joinRequestService;

    public JoinRequestController(JoinRequestService joinRequestService) {
        this.joinRequestService = joinRequestService;
    }

    @PostMapping("/join-requests")
    public MessageResponseDto createJoinRequest(@RequestBody JoinRequestDto joinRequestDto) {

        JoinRequest joinRequest = JoinRequest.builder()
                .fName(joinRequestDto.fName())
                .phone(joinRequestDto.phone())
                .email(joinRequestDto.email())
                .degree(joinRequestDto.degree())
                .major(joinRequestDto.major())
                .cellsOfInterest(joinRequestDto.cellsOfInterest())
                .topicsOfInterest(joinRequestDto.topicsOfInterest())
                .message(joinRequestDto.message())
                .build();

        joinRequestService.save(joinRequest);

        return MessageResponseDto.builder().message("Join request created successfully").success(true).build();
    }
}
