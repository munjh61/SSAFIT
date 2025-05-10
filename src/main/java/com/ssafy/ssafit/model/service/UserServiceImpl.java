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

    @Override
    public boolean valid(User user) {
        User tmp = select(user.getUserId());
        return passwordEncoder.matches(user.getPassword(), tmp.getPassword());
    }

    @Transactional
    @Override
    public boolean regist(User user) {
        if (select(user.getUserId()) == null) {
            String pass = user.getPassword();
            pass = passwordEncoder.encode(pass);
            user.setPassword(pass);
            insert(user);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public int insert(User user) {
        return userDao.insert(user);
    }

    @Override
    public User select(String userId) {
        return userDao.select(userId);
    }

    @Transactional
    @Override
    public int update(User user) {
        User tmp = select(user.getUserId());
        if (user.getUserName() == null) {
            user.setUserName(tmp.getUserName());
        }
        if (user.getPassword() == null) {
            user.setPassword(tmp.getPassword());
        }
        if (user.getEmail() == null) {
            user.setEmail(tmp.getEmail());
        }
        return userDao.update(user);
    }

    @Transactional
    @Override
    public int delete(String userId) {
        return userDao.delete(userId);
    }

}
