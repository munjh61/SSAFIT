package com.ssafy.ssafit.controller.user;

import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.model.service.EmailService;
import com.ssafy.ssafit.model.service.GuildService;
import com.ssafy.ssafit.model.service.UserService;
import com.ssafy.ssafit.security.CustomUserDetails;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final GuildService guildService;

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userDetails.getUser());
    }
    // 회원정보 변경
    @PutMapping
    public ResponseEntity<String> changeUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody User user){
        String email = user.getEmail();
        if(email != null){
            if(!emailService.isVerifiedEmail(email)){
                return new ResponseEntity<>("인증 처리되지 않은 이메일입니다..", HttpStatus.BAD_REQUEST);
            }
            String tmpId = userService.findUserIdByEmail(email);
            if(tmpId==null){
                return new ResponseEntity<>("이메일 인증은 되었으나, 해당 이메일을 사용하는 계정이 존재하지 않습니다..", HttpStatus.BAD_REQUEST);
            }
        }
        user.setUserId(userDetails.getUsername());
        userService.update(user);
        return new ResponseEntity<>("회원 정보가 변경되었습니다", HttpStatus.OK);
    }
    // 회원 탈퇴
    @DeleteMapping
    public ResponseEntity<String> changeUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails){
        String userId = userDetails.getUsername();
        userService.delete(userId);
        guildService.quitSSAFIT(userId);
        return new ResponseEntity<>("탈퇴 처리되었습니다.", HttpStatus.OK);
    }

}
