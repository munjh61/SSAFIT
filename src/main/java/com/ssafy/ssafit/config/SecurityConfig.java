package com.ssafy.ssafit.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    static final String frontURL = "http://localhost:5173";

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // csrf
                .csrf(csrf -> csrf.disable())
                // cors 설정
                .cors(cors -> cors.configurationSource(ccfs()))
                // 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**/public/**").permitAll()
                        .anyRequest().authenticated()
                );
        return httpSecurity.build();
    }

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

}
