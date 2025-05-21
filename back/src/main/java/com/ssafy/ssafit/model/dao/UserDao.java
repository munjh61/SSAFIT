package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.User;

public interface UserDao {
    int insert(User user);
    User select(String userId);
    int update(User user);
    int delete(String userId);
    User selectByEmail(String email);
}
