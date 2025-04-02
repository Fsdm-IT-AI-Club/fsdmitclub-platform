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

package com.fsdm.it.fsdm_it_club.config;

import com.fsdm.it.fsdm_it_club.dto.response.MessageResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UrlAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

//        System.out.printf("Authentication failed: %s%n", exception.getMessage());
//
//
//        String loginType = request.getParameter("loginType");
//        if ("admin".equals(loginType)) {
//            response.sendRedirect("/admin/login?error=true");
//        } else {
//            response.sendRedirect("/login?error=true");
//        }

       // return message dto
        MessageResponseDto messageResponseDto = new MessageResponseDto("Authentication failed: " + exception.getMessage(),false);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        // add message and success response dto to response as json
        response.getWriter().write("{\"message\": \"" + messageResponseDto.getMessage() + "\", \"success\": " + messageResponseDto.isSuccess() + "}");
        response.getWriter().flush();
        response.getWriter().close();
    }
}
