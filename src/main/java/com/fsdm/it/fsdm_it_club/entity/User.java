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

package com.fsdm.it.fsdm_it_club.entity;

import com.fsdm.it.fsdm_it_club.converters.ListStringToStringConverter;
import com.fsdm.it.fsdm_it_club.model.enums.Degree;
import com.fsdm.it.fsdm_it_club.util.RoleAuthorityMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    protected final static String ROLE_PREFIX = "ROLE_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String fName;
    private String lName;

    private String username;

    @Column(unique = true)
    private String email;
    private String password;
    private Role role;
    private String phone;
    private String cne;
    private String address;
    private String city;

    private String major;
    private String biography;
    private String image;

    @Convert(converter = ListStringToStringConverter.class)
    private List<String> passions;

    private String linkedin;
    private String github;

    private Degree degree;

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + this.getRole()));
        for (User.Authority authority : RoleAuthorityMapper.getAuthorities(this.getRole())) {
            authorities.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public enum Role {
        PRESIDENT("President"),
        VICE_PRESIDENT("Vice Presedent"),
        SECRETARY("Secretary"),
        TREASURER("Treasurer"),
        DESIGNER("Designer"),
        EVENT_MANAGER("Event Manager"),
        LEAD("Lead"),
        MEMBER("Member");

        String roleName;

        Role(String roleName) {
            this.roleName = roleName;
        }

        public String getRoleName() {
            return roleName;
        }

    }

    public enum Authority {
        CREATE_EVENT,
        DELETE_EVENT,
        VIEW_MEMBERS,
        VIEW_EVENTS,
        VIEW_JOIN_REQUESTS,
        VIEW_ADMIN,
        VIEW_ADMIN_DASHBOARD,
        VIEW_ADMIN_MEMBERS,
        VIEW_ADMIN_EVENTS,
        VIEW_SUBSCRIBED_EMAILS,
        EDITE_SUBSCRIBED_EMAILS;


        public String getAuthorityName() {
            return this.name();
        }

    }

}
