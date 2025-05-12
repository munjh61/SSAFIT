package com.ssafy.ssafit.controller.user;

import com.ssafy.ssafit.model.service.UserService;
import com.ssafy.ssafit.security.CustomUserDetails;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/public/user")
@RequiredArgsConstructor
public class UserController {

    // private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userDetails.getUser());
    }

    // 회원정보 변경
    //@PutMapping
    // 회원 탈퇴
    //@DeleteMapping

}
