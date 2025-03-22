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

package com.fsdm.it.fsdm_it_club.controllers.admin;


import com.fsdm.it.fsdm_it_club.dto.request.PaginateTableRequestDto;
import com.fsdm.it.fsdm_it_club.dto.response.JoinRequestListItemResponseDto;
import com.fsdm.it.fsdm_it_club.dto.response.JoinRequestsResponseDto;
import com.fsdm.it.fsdm_it_club.services.JoinRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.format.DateTimeFormatter;

@Controller
public class AdminJoinRequestController {

   private final JoinRequestService joinRequestService;

   public AdminJoinRequestController(JoinRequestService joinRequestService) {
      this.joinRequestService = joinRequestService;
   }

    @GetMapping("/admin/join-requests")
    public String joinRequestsList() {
        return "admin/join-requests";
    }

   @PostMapping("/admin/join-requests/list")
   @ResponseBody
   public ResponseEntity<?> getAllJoinRequests(@RequestBody PaginateTableRequestDto requestDto) {
      int page = requestDto.page();
      Pageable pageable = PageRequest.of(page, requestDto.length());
      var eventsList = joinRequestService.findByFNameContainingIgnoreCase(requestDto.search(), requestDto.order(), pageable);
      Page<JoinRequestListItemResponseDto> joinRequestListItemResponseDtoPage = eventsList.map(joinRequest -> {
         return new JoinRequestListItemResponseDto(
                 joinRequest.getId(),
                 joinRequest.getFName(),
                 joinRequest.getPhone(),
                 joinRequest.getEmail(),
                 joinRequest.getTopicsOfInterest(),
                 joinRequest.getCellsOfInterest(),
                 joinRequest.getDegree(),
                 joinRequest.getMajor(),
                 joinRequest.getMessage(),
                 joinRequest.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
         );
      });

      JoinRequestsResponseDto response = JoinRequestsResponseDto.builder().draw(requestDto.draw())
              .recordsTotal(eventsList.getTotalElements())
              .recordsFiltered(eventsList.getTotalElements())
              .data(joinRequestListItemResponseDtoPage.getContent())
              .build();

      return ResponseEntity.ok(response);

   }
}
