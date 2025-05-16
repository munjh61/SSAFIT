package com.ssafy.ssafit.security;

import com.ssafy.ssafit.model.dto.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    @Value("${jwt.secret}") // application.properties에서 불러올 예정
    private String secretKeyPlain;

    private Key key;

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7 * 2; // 2주

    @PostConstruct
    // @PostConstruct : Bean이 다 불러지고 난 이후에 메서드를 실행하게 하는 어노테이션
    // @PostConstruct 없으면 이 클래스가 켜질 때, secretKeyPlain 값이 아직 null 일 수 있음.
    public void init() {
        // String인 key를 Key 객체로 바꿔주는 메서드
        this.key = Keys.hmacShaKeyFor(secretKeyPlain.getBytes());
    }

    // 1. JWT 생성
    // JWT는 <Header>.<Payload>.<Signature> 로 구성되며, HS256은 signature 방식 중 대칭키로 제일 많이 쓰이는 방식
    public String generateToken(User user) {
        Map<String, Object> claim = new HashMap<>();
        claim.put("userId", user.getUserId());
        claim.put("userName", user.getUserName());
        claim.put("role", user.getRole());
        return Jwts.builder()
                // JJWT는 기본적으로 {"alg": "HS256", "typ": "JWT"} 를 자동으로 넣어줘서 header 생략
                //.setHeaderParam("typ", "JWT")

                //.setSubject(userId) 사용시
                // payload {
                // sub : userId
                //} 이렇게 저장됨.

                // addclaims(Map객체)를 사용하면 필드명도 바꿀 수 있고 여러개를 넣을 수 있다.
                .addClaims(claim)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 2. JWT에서 사용자 ID 추출
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                // 검증에 사용할 서명 키 지정
                .setSigningKey(key)
                // JwtParser 객체 생성
                .build()
                // 토큰을 파싱
                .parseClaimsJws(token)
                // payload 가져오기
                .getBody()
                //.getSubject(); sub라는 항목 가져옴
                // .get("key", class) 하면 해당 데이터 가져옴
                .get("userId", String.class);

    }

    // 3. JWT 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            // 토큰을 파싱만 해도 유효한 토큰인지 검증이 됨
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}