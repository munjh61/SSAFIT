package com.ssafy.ssafit.security;

import com.ssafy.ssafit.model.dto.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // 권한 반환 (단일 권한만 있는 경우)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "ROLE_" + user.getRole());
    }

    // 비밀번호는 사용하지 않을 수도 있음 (JWT 기반)
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId(); // 식별자
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 필요 시 로직 추가
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 필요 시 로직 추가
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 필요 시 로직 추가
    }

    @Override
    public boolean isEnabled() {
        return !user.isDeleted(); // 예: 탈퇴 계정 처리
    }
}
