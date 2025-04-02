/*
 * Copyright (c) 2024, 2025 FSDM IT Club.
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

import com.fsdm.it.fsdm_it_club.entity.User;
import com.fsdm.it.fsdm_it_club.services.NewsletterEmailService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.authorization.method.HandleAuthorizationDenied;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    private final NewsletterEmailService newsletterEmailService;

    public AdminController(NewsletterEmailService newsletterEmailService) {
        this.newsletterEmailService = newsletterEmailService;
    }

//    @PreAuthorize("hasAuthority(User.Authority.VIEW_DASHBOARD)")
//    @HandleAuthorizationDenied()
    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/events")
    public String users() {
        return "admin/events";
    }

    @GetMapping("/admin/members")
    public String members() {
        return "admin/members";
    }

    @GetMapping("/admin/members/add")
    public String addMember(Model model) {
        User.Role[] roles = User.Role.values();
        Map<String, String> rolesMap = Arrays.stream(roles).collect(Collectors.toMap(e -> e.name(), e -> e.getRoleName()));
        model.addAttribute("memberRoles", rolesMap);

        return "admin/members/add";
    }

}
