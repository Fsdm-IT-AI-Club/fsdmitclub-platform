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

package com.fsdm.it.fsdm_it_club.util;

import com.fsdm.it.fsdm_it_club.entity.User;

import java.util.EnumSet;
import java.util.Set;

public class RoleAuthorityMapper {

    public static Set<User.Authority> getAuthorities(User.Role role) {
        switch (role) {
            case PRESIDENT:
                return EnumSet.of(User.Authority.CREATE_EVENT, User.Authority.DELETE_EVENT, User.Authority.VIEW_MEMBERS, User.Authority.VIEW_ADMIN, User.Authority.VIEW_ADMIN_DASHBOARD, User.Authority.VIEW_ADMIN_MEMBERS, User.Authority.VIEW_ADMIN_EVENTS, User.Authority.VIEW_JOIN_REQUESTS, User.Authority.VIEW_EVENTS, User.Authority.VIEW_SUBSCRIBED_EMAILS, User.Authority.EDITE_SUBSCRIBED_EMAILS, User.Authority.EDITE_EVENTS);
            case VICE_PRESIDENT:
                return EnumSet.of(User.Authority.CREATE_EVENT, User.Authority.VIEW_MEMBERS, User.Authority.VIEW_ADMIN, User.Authority.VIEW_ADMIN_DASHBOARD, User.Authority.VIEW_ADMIN_MEMBERS, User.Authority.VIEW_ADMIN_EVENTS);
            case SECRETARY:
                return EnumSet.of(User.Authority.VIEW_MEMBERS, User.Authority.VIEW_EVENTS, User.Authority.VIEW_JOIN_REQUESTS);
            case TREASURER:
                return EnumSet.of(User.Authority.VIEW_MEMBERS, User.Authority.VIEW_EVENTS);
            case DESIGNER:
                return EnumSet.of(User.Authority.VIEW_EVENTS);
            case EVENT_MANAGER:
                return EnumSet.of(User.Authority.CREATE_EVENT, User.Authority.VIEW_EVENTS);
            case LEAD:
                return EnumSet.of(User.Authority.VIEW_MEMBERS, User.Authority.VIEW_EVENTS);
            case MEMBER:
                return EnumSet.of(User.Authority.VIEW_EVENTS);
            default:
                return EnumSet.noneOf(User.Authority.class);
        }
    }
}