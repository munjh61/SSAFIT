package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.User;

public interface UserService {
    //create
    boolean insert(User user);
    //read
    User select(String userId);
    //update
    int update(User user);
    //delete
    int delete(String userId);

    //중복확인
    boolean isExistingId(String userId);
    boolean isExistingEmail(String email);
    //아이디 찾기
    String findUserIdByEmail(String email);
}
