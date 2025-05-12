package com.ssafy.ssafit.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Authorization 헤더에서 토큰 추출
        // 프론트에서 보낸 헤더 중 Authorization: Bearer <token>을 찾아 없거나 형식 이상하면 바로 다음 필터로 넘김 (비인증 상태)
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // "Bearer " 다음부터

        // 2. 토큰 유효성 검사
        // 토큰 값이 없다면 다음 필터로 넘김 (비인증 상태)
        if (!jwtProvider.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 유저 정보 로드 및 인증 객체 등록
        // 나중에 헷갈릴까봐 적음 : 이미 로그인을 한 상태라서 토큰이 있는 것이고, 매번 올 때마다 필터로 누구인지 securityContextHolder에 등록하는 거임
        // 로그인 메서드로 토큰 발급하는 곳은 다른 곳에 있다.
        // 인증 객체(Authentication)를 직접 생성해서 SecurityContextHolder에 등록 → Spring Security가 "인증된 사용자"로 간주하게 함
        String username = jwtProvider.getUsernameFromToken(token);
        var userDetails = customUserDetailsService.loadUserByUsername(username);
        var authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
        // 이제 컨트롤러에서는 @AuthenticationPrincipal 같은 걸 써도 로그인한 사용자로 접근 가능
    }
}