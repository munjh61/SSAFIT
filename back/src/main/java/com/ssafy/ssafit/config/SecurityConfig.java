package com.ssafy.ssafit.config;

import com.ssafy.ssafit.security.JwtAuthenticationFilter;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private String frontURL = "http://localhost:5173";
    // private final CustomOAuth2UserService oAuth2UserService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // csrf
                // jwt를 쓰는 경우 스프링 시큐리티의 기본 로그인을 사용하지 않고 커스텀하는 것이라 csrf를 꺼야함
                .csrf(csrf -> csrf.disable())
                // cors 설정
                .cors(cors -> cors.configurationSource(ccfs()))
                // session 보호 종료
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        ;
        return httpSecurity.build();
    }

    // cors 설정
    @Bean
    public CorsConfigurationSource ccfs(){
        // 어떤 config인지 설정
        CorsConfiguration ccf = new CorsConfiguration();
        ccf.addAllowedOrigin(frontURL);
        ccf.addAllowedMethod("*");
        ccf.addAllowedHeader("*");
        ccf.setAllowCredentials(true);
        // 위에서 정의한 config를 어디에 적용할지 설정
        UrlBasedCorsConfigurationSource ubccfs = new UrlBasedCorsConfigurationSource();
        ubccfs.registerCorsConfiguration("/**",ccf);

        return ubccfs;
    }

    // 로그인 관련
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    // 비밀번호 잠금 || 해독
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
