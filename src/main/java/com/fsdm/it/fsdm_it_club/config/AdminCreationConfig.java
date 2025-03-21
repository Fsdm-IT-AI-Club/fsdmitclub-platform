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


import com.fsdm.it.fsdm_it_club.entity.User;
import com.fsdm.it.fsdm_it_club.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminCreationConfig {
   @Value("${admin.email}")
   String adminEmail;
   @Value("${admin.password}")
   String adminPassword;

   private final UserService userService;
   private final PasswordEncoder passwordEncoder;

   public AdminCreationConfig(UserService userService, PasswordEncoder passwordEncoder) {
      this.userService = userService;

      this.passwordEncoder = passwordEncoder;
   }

   @Bean
   public ApplicationRunner createAdminRunner() {
      return args -> {

         // Check if the admin user already exists
         if (userService.loadUserByEmail(adminEmail).isEmpty()) {


            User admin = new User();
            admin.setFName("Admin");
            admin.setLName("Admin");
            admin.setUsername(adminEmail);
            admin.setEmail(adminEmail);
            admin.setRole(User.Role.PRESIDENT);

            admin.setPassword(passwordEncoder.encode(adminPassword));
            userService.saveUser(admin);
         }
      };
   }
}
