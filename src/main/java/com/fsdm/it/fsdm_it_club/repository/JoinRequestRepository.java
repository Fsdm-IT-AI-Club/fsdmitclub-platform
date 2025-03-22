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

import com.fsdm.it.fsdm_it_club.entity.JoinRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    // search by name and email and phone with ignore case with pagination for one search field
    Page<JoinRequest> findByfNameContainingIgnoreCaseOrderByIdAsc(String query, Pageable pageable);
    Page<JoinRequest> findByfNameContainingIgnoreCaseOrderByIdDesc(String query, Pageable pageable);


    Page<JoinRequest> findByfNameContainingIgnoreCaseOrderByFNameAsc(String query, Pageable pageable);
    Page<JoinRequest> findByfNameContainingIgnoreCaseOrderByFNameDesc(String query, Pageable pageable);
}