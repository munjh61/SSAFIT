package com.ssafy.ssafit.controller.user;

import com.ssafy.ssafit.model.service.EmailService;
import com.ssafy.ssafit.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/public/user")
@RequiredArgsConstructor
public class UserPublicController {

    // private final UserService userService;
    private final EmailService emailService;

    // 이메일 인증 요청
    @PostMapping("/email/request")
    public ResponseEntity<String> emailRequest(@RequestBody Map<String, String> body){
        String email = body.get("email");
        if (email == null || email.isBlank()) {
            return new ResponseEntity<>("이메일이 비어있거나 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        if(!emailService.sendCode(email)){
            return new ResponseEntity<>("메일 전송에 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("인증번호가 전송되었습니다.", HttpStatus.CREATED);
    }

    // 이메일 인증 확인
    @PostMapping("/email/verify")
    public ResponseEntity<String> emailVerify(@RequestBody Map<String, String> body){
        System.out.println(1);
        String email = body.get("email");
        System.out.println(2);
        String code = body.get("code");
        System.out.println(3);
        if(email == null || email.isBlank()){
            return new ResponseEntity<>("이메일이 비어있거나 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        if(code == null || code.isBlank()){
            return new ResponseEntity<>("코드 입력이 되지 않았습니다.", HttpStatus.BAD_REQUEST);
        }
        if(!emailService.checkCode(email, code)){
            return new ResponseEntity<>("메일 인증에 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("메일 인증에 성공하였습니다.", HttpStatus.OK);
    }
    // 중복된 이메일 확인
    //@GetMapping("/email/{email}")
    // 중복된 아이디 확인
    //@GetMapping("/id/{id}")
    // 아이디 찾기
    //@GetMapping("/find")
    // 회원가입
    //@PostMapping()
}
