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
import com.fsdm.it.fsdm_it_club.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;


public class UrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        System.out.printf("Authentication successful: %s%n", authentication.getName());
//        //Logic
//        var authaurities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
//        if(authaurities.contains(User.Role.MEMBER.name())) {
//            // redirect to memeber page
//            this.getRedirectStrategy().sendRedirect(request,response,request.getContextPath() + "/");
//        }else{
//            // redirect to dashboard  page depending on the role // index
//            //if user should see admin/index
//
//            this.getRedirectStrategy().sendRedirect(request,response,request.getContextPath() + "/admin");
//        }
//        //
//        super.onAuthenticationSuccess(request, response, authentication);
        MessageResponseDto messageResponseDto = new MessageResponseDto("Authentication successful: " + authentication.getName(), true);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        // add message and success response dto to response as json
        response.getWriter().write("{\"message\": \"" + messageResponseDto.getMessage() + "\", \"success\": " + messageResponseDto.isSuccess() + "}");
        response.getWriter().flush();
        response.getWriter().close();

    }

}
