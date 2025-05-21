package org.backend.security;


import org.backend.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Collections;


public class CustomUserDetails implements UserDetails {


    private final Users user;


    public CustomUserDetails(Users user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ROLE_ADMIN, ROLE_STUDENT, ROLE_PROFESSOR
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }


    @Override
    public String getUsername() {
        return user.getEmail();
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


    public Users getUser() {
        return user;
    }
}
