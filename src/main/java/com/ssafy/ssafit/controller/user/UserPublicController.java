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

    private final UserService userService;
    private final EmailService emailService;

    // 이메일 인증 요청
    @PostMapping("/email/request")
    public ResponseEntity<String> emailRequest(@RequestBody Map<String, String> body){
        String address = body.get("address");
        if (address == null || address.isBlank()) {
            return new ResponseEntity<>("이메일이 비어있거나 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        if(!emailService.sendCode(address)){
            return new ResponseEntity<>("메일 전송에 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("인증번호가 전송되었습니다.", HttpStatus.CREATED);
    }

    // 이메일 인증 확인
    @PostMapping("/email/verify")
    public ResponseEntity<String> emailVerify(@RequestBody Map<String, String> body){
        String address = body.get("address");
        String code = body.get("code");
        if(address == null || address.isBlank()){
            return new ResponseEntity<>("이메일이 비어있거나 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        if(code == null || code.isBlank()){
            return new ResponseEntity<>("코드 입력이 되지 않았습니다.", HttpStatus.BAD_REQUEST);
        }
        if(!emailService.checkCode(address, code)){
            return new ResponseEntity<>("메일 인증에 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("메일 인증에 성공하였습니다.", HttpStatus.OK);
    }
    // 중복된 이메일 확인
    @GetMapping("/email/{email}")
    public ResponseEntity<String> existingEmail(@PathVariable("email") String address){
        if(userService.isExistingId(address)){
            return new ResponseEntity<>("이미 존재하는 이메일 계정입니다..", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("사용할 수 있는 이메일 계정입니다.", HttpStatus.OK);
    }
    // 중복된 아이디 확인
    @GetMapping("/id/{id}")
    public ResponseEntity<String> existingUserId(@PathVariable("id") String id){
        if(userService.isExistingId(id)){
            return new ResponseEntity<>("이미 존재하는 아이디입니다..", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("사용할 수 있는 아이디입니다.", HttpStatus.OK);
    }
    // 아이디 찾기
    @GetMapping("/find/{email}")
    public ResponseEntity<String> findId(@PathVariable("email") String email){
        if(!emailService.isVerifiedEmail(email)){
            return new ResponseEntity<>("인증 처리되지 않은 이메일입니다..", HttpStatus.BAD_REQUEST);
        }
        String tmpId = userService.findUserIdByEmail(email);
        if(tmpId==null){
            return new ResponseEntity<>("이메일 인증은 되었으나, 해당 이메일을 사용하는 계정이 존재하지 않습니다..", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tmpId, HttpStatus.OK);
    }

    // 회원가입
    //@PostMapping()
}
