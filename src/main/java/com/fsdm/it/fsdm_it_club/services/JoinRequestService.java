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


import com.fsdm.it.fsdm_it_club.entity.JoinRequest;
import com.fsdm.it.fsdm_it_club.events.JoinRequestCreatedEvent;
import com.fsdm.it.fsdm_it_club.model.TableSortOrder;
import com.fsdm.it.fsdm_it_club.model.enums.SortOrder;
import com.fsdm.it.fsdm_it_club.repository.JoinRequestRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JoinRequestService {
    private final JoinRequestRepository joinRequestRepository;
    private final ApplicationEventPublisher eventPublisher;

    public JoinRequestService(JoinRequestRepository joinRequestRepository, ApplicationEventPublisher eventPublisher) {
        this.joinRequestRepository = joinRequestRepository;
        this.eventPublisher = eventPublisher;
    }

    public void save(JoinRequest joinRequest) {
        joinRequestRepository.save(joinRequest);
        eventPublisher.publishEvent(new JoinRequestCreatedEvent(joinRequest));
    }

    public JoinRequest findById(Long id) {
        return joinRequestRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        joinRequestRepository.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        JoinRequest joinRequest = joinRequestRepository.findById(id).orElse(null);
        if (joinRequest != null) {
            joinRequest.setStatus(status);
            joinRequestRepository.save(joinRequest);
        }
    }

    public Page<JoinRequest> findByFNameContainingIgnoreCase(String query, List<TableSortOrder> order, Pageable pageable) {

        if (order.isEmpty()) {
            return joinRequestRepository.findByfNameContainingIgnoreCaseOrderByIdAsc(query, pageable);
        }

        TableSortOrder sortOrder = order.get(0);

        SortOrder direction = sortOrder.dir();

        if (direction == SortOrder.ASC) {
            return joinRequestRepository.findByfNameContainingIgnoreCaseOrderByIdAsc(query, pageable);
        } else {
            return joinRequestRepository.findByfNameContainingIgnoreCaseOrderByIdDesc(query, pageable);
        }


    }
}
