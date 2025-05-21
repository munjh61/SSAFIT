package com.ssafy.ssafit.security;

import com.ssafy.ssafit.model.dao.UserDao;
import com.ssafy.ssafit.model.dto.User;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserDao userDao;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public String login(String userId, String rawPassword) {
        User user = userDao.select(userId);

        if (user == null) {
            throw new RuntimeException("존재하지 않는 아이디입니다.");
        }

//        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
//            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
//        }
        
        // 임시 데이터 확인용
        if (!rawPassword.equals(user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return jwtProvider.generateToken(user);
    }
}

