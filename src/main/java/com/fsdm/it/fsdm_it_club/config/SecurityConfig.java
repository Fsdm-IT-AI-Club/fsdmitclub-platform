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
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
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
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/login")).permitAll(); // Add this line


                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/test/**")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/register*")).permitAll();

                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/logout*")).hasRole(User.Role.MEMBER.name());
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/verify*")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/public/**")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/reset-password")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/reset-password-request")).permitAll();
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/join-requests*")).permitAll();



                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/login")).permitAll();
                            // resources
                            // admin resources
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


                            // admin endpoints
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin*")).hasAuthority(User.Authority.VIEW_ADMIN_DASHBOARD.getAuthorityName());
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/events/calendar")).hasAuthority(User.Authority.VIEW_ADMIN_DASHBOARD.getAuthorityName());
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/join-requests/**")).hasAuthority(User.Authority.VIEW_JOIN_REQUESTS.getAuthorityName());
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/events/add")).hasAuthority(User.Authority.CREATE_EVENT.getAuthorityName());
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/events/list")).hasAuthority(User.Authority.VIEW_EVENTS.getAuthorityName());
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/newsletter")).hasAuthority(User.Authority.VIEW_SUBSCRIBED_EMAILS.getAuthorityName());
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/newsletter/unsubscribe/**")).hasAuthority(User.Authority.EDITE_SUBSCRIBED_EMAILS.getAuthorityName());
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/newsletter/delete/**")).hasAuthority(User.Authority.EDITE_SUBSCRIBED_EMAILS.getAuthorityName());
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/newsletter/subscribe/**")).hasAuthority(User.Authority.EDITE_SUBSCRIBED_EMAILS.getAuthorityName());


                        }

                )
                .formLogin(login -> {
                    login.loginPage("/login").permitAll().successHandler(new UrlAuthenticationSuccessHandler()).
                            failureHandler(new UrlAuthenticationFailureHandler());
                })
                .sessionManagement(sessionManagement -> {
                    // define the session management tto enable only one session per time

                    sessionManagement.maximumSessions(1).maxSessionsPreventsLogin(false)
                            .expiredUrl("/?expired=true");
                })

                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutSuccessHandler(new UrlLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        {
                            httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint((request, response, authException) -> {


                                // ge loginType from the request
//                                if (request.getRequestURI().contains("/admin")) {
//                                    response.sendRedirect("/admin/login");
//                                } else {
//                                    response.sendRedirect("/");
//                                }
                                // send costum response as text
                                response.setContentType("text/plain");
                                response.setStatus(401);
                                response.getWriter().write(authException.fillInStackTrace().toString());
                            });
                        }
                );
//                .addFilter(new CustomAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))));

        return http.build();
    }


}
