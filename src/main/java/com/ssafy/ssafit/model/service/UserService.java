package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.User;

public interface UserService {
    //create
    int insert(User user);
    //read
    User select(String userId);
    //update
    int update(User user);
    //delete
    int delete(String userId);

    //login
    boolean valid(User user);
    //regist
    boolean regist(User user);
}
