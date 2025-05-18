package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.UserDao;
import com.ssafy.ssafit.model.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public boolean insert(User user) {
        if (select(user.getUserId()) == null) {
            String password = passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
            userDao.insert(user);
            return true;
        }
        return false;
    }

    @Override
    public User select(String userId) {
        return userDao.select(userId);
    }

    @Transactional
    @Override
    public int update(User user) {
        User tmp = select(user.getUserId());
        if (user.getUserName() != null) {
            user.setUserName(tmp.getUserName());
        }
        if (user.getPassword() != null) {
            String password = passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
        }
        if (user.getEmail() != null) {
            user.setEmail(tmp.getEmail());
        }
        return userDao.update(user);
    }

    @Transactional
    @Override
    public int delete(String userId) {
        return userDao.delete(userId);
    }

    @Override
    public boolean isExistingId(String userId) {
        User tmp = userDao.select(userId);
        return tmp != null;
    }

    @Override
    public boolean isExistingEmail(String email) {
        User tmp = userDao.selectByEmail(email);
        return tmp != null;
    }

    @Override
    public String findUserIdByEmail(String email) {
        User tmp = userDao.selectByEmail(email);
        if(tmp == null){
            return null;
        }
        String transformId = tmp.getUserId();
        StringBuilder sb = new StringBuilder();
        int length = transformId.length();
        for(int i = 0 ; i < length; i++){
            if(transformId.charAt(i)=='@'){
                for(int j = 0; j < 2 ; j++){
                    sb.append(transformId.charAt(j));
                }
                sb.append("******");
                for(int j = i -2 ; j < length ; j++){
                    if(j >= 0){
                        sb.append(transformId.charAt(j));
                    }
                }
            }
        }
        return sb.toString();
    }

}
