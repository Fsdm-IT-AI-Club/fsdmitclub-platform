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

package com.fsdm.it.fsdm_it_club.config;


import com.fsdm.it.fsdm_it_club.entity.User;
import com.fsdm.it.fsdm_it_club.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManagerAdmin(
            UserService userService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http.authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                        {
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/public/**")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/event-listing")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/event-details")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/join-us")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/test/**")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/register*")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/login*")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/logout*")).hasRole(User.Role.MEMBER.name());
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/verify*")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/public/**")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/reset-password")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/reset-password-request")).permitAll();

                            //admin
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin*")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/login")).permitAll();
                            // resources
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/css/**")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/js/**")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/img/**")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/fonts/**")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/scss/**")).permitAll();

                            // resource for home
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/home/css/**")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/home/js/**")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/home/images/**")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/home/fonts/**")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/home/scss/**")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/home/chatbot/**")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/home/toaster/**")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/home/alert/**")).permitAll();



                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/join-requests")).permitAll();




                            //authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/**")).access("isAuthenticated() OR hasAuthority('read')")
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/**")).permitAll();
//                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/**")).hasRole(Role.ADMIN_ROLE);

                        }

                )
                .formLogin(login -> {
                    login.loginPage("/login").permitAll().successHandler(new UrlAuthenticationSuccessHandler()).
                            failureHandler(new UrlAuthenticationFailureHandler());
                })
//                .sessionManagement(sessionManagement -> {
//                    // define the session management tto enable only one session per time
//                    sessionManagement.maximumSessions(1).maxSessionsPreventsLogin(false)
//                            .expiredUrl("/?expired=true");
//                })

                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutSuccessHandler(new UrlLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID"))
//                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
//                        {
//                            httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint((request, response, authException) -> {
//                                // ge loginType from the request
//                                if (request.getRequestURI().contains("/admin")) {
//                                    response.sendRedirect("/admin/login");
//                                } else {
//                                    response.sendRedirect("/");
//                                }
//                            });
//                        }
//                )
        ;
        return http.build();
    }

}
