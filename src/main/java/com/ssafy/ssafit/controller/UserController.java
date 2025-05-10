package com.ssafy.ssafit.controller;

import com.ssafy.ssafit.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    // 로그인
    //@PostMapping("/public/login")
    // 이메일 인증 요청
    //@PostMapping("/public/email/request")
    // 이메일 인증 확인
    //@PostMapping("/public/email/verify")
    // 중복된 아이디 확인
    //@GetMapping("/public/id/{id}")
    // 중복된 이메일 확인
    //@GetMapping("/public/emial/{email}")
    // 아이디 찾기
    //@GetMapping("/public/find")
    // 비밀번호 재설정
    //@Update("/public/reset")
    // 회원가입
    //@PostMapping("/public")
    // 회원정보 변경
    //@PutMapping
    // 회원 탈퇴
    //@DeleteMapping

}
