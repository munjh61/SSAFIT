package com.ssafy.ssafit.security;

import com.ssafy.ssafit.model.dto.User;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        Map<String, Object> map = loginService.login(user.getUserId(), user.getPassword());
        boolean success = (boolean) map.get("result");
        String msg = (String)map.get("msg");
        if(!success){
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        String token = (String) map.get("token");
        map.put("token", token);

        return ResponseEntity.ok(token);
    }
}

