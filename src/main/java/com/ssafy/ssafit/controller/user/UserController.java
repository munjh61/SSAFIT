package com.ssafy.ssafit.controller.user;

import com.ssafy.ssafit.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원정보 변경
    //@PutMapping
    // 회원 탈퇴
    //@DeleteMapping

}
