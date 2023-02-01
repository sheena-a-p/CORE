package com.core.system.util;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserAccess implements UserDetails {

        private static final List<GrantedAuthority> ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
        public final int userId;
        private String userRole;

        public UserAccess(int userId) {
            this.userId = userId;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            String ROLE_PREFIX = "ROLE_";
            List<GrantedAuthority> ROLES = new ArrayList<>();
            ROLES.add(new SimpleGrantedAuthority(ROLE_PREFIX + userRole));
            return ROLES;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public String getUsername() {
            return null;
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
    }
