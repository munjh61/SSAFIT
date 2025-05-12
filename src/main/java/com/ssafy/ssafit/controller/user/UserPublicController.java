package com.ssafy.ssafit.controller.user;

import com.ssafy.ssafit.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/public")
@RequiredArgsConstructor
public class UserPublicController {

    // private final UserService userService;

    // 이메일 인증 요청
    //@PostMapping("/email/request")
    // 이메일 인증 확인
    //@PostMapping("/email/verify")
    // 중복된 이메일 확인
    //@GetMapping("/email/{email}")
    // 중복된 아이디 확인
    //@GetMapping("/id/{id}")
    // 아이디 찾기
    //@GetMapping("/find")
    // 회원가입
    //@PostMapping()
}
