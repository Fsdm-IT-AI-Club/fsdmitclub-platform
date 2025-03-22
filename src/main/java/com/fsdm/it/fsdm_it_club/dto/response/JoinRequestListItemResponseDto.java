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

package com.fsdm.it.fsdm_it_club.dto.response;


import lombok.Builder;

@Builder
public record JoinRequestListItemResponseDto(
        Long id,
        String fName,
        String phone,
        String email,
        java.util.List<String> topicsOfInterest,
        java.util.List<String> cellsOfInterest,
        String degree,
        String major,
        String message,
        String createdAt
) {
}