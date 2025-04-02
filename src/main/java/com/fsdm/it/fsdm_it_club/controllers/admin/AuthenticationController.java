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

import com.fsdm.it.fsdm_it_club.dto.response.LoginResponseDto;
import com.fsdm.it.fsdm_it_club.entity.User;
import com.fsdm.it.fsdm_it_club.exceptions.personalizedexceptions.CustomerLoginException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fsdm.it.fsdm_it_club.dto.request.LoginRequestDto;

@Controller
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final Validator validator;
    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    public AuthenticationController(@Qualifier("authenticationManager") AuthenticationManager authenticationManager, Validator validator) {
        this.authenticationManager = authenticationManager;
        this.validator = validator;
    }

    @GetMapping("admin/login")
    public String login() {
        return "admin/sign-in";
    }

//    @PostMapping("/admin/login")
//    public ResponseEntity<?> loginCustomer(@RequestBody LoginRequestDto loginRequest, HttpServletRequest request, HttpServletResponse response) {
//        try {
//            // Create an authentication request using the provided username and password
//            Authentication authenticationRequest =
//                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
//
//            // Attempt to authenticate the user
//            Authentication authenticationResponse =
//                    this.authenticationManager.authenticate(authenticationRequest);
//
//            SecurityContext context = SecurityContextHolder.createEmptyContext();
//            context.setAuthentication(authenticationResponse);
//            securityContextRepository.saveContext(context, request, response);
//
//            if (authenticationResponse.isAuthenticated()) {
//                User user = (User) authenticationResponse.getPrincipal();
//            }
//
//            return ResponseEntity.ok(new LoginResponseDto(true, authenticationResponse.isAuthenticated(), null, "Login successful"));
//
//        } catch (CustomerLoginException | UsernameNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDto(false, false, e.getMessage(), e.getMessage()));
//        } catch (BadCredentialsException e) {
//            // Handle incorrect password
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDto(false, false, e.getMessage(), "Incorrect Email or Password"));
//        } catch (AuthenticationException e) {
//            // Handle other authentication failures
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDto(false, false, e.getMessage(), e.getMessage()));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDto(false, false, e.getMessage(), e.getMessage()));
//        }
//    }

}
