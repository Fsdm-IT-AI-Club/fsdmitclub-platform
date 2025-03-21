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


import jakarta.validation.constraints.*;

import java.util.List;

public record JoinRequestDto(@NotNull @NotEmpty @Min(4) @Max(50) String fName, @NotNull @NotEmpty @Min(10) @Max(14) String phone, @Email String email, List<String> topicsOfInterest, List<String> cellsOfInterest, @Min(5) @Max(50) String degree, @Min(5) @Max(50) String major, @Max(500) String message) {
}
