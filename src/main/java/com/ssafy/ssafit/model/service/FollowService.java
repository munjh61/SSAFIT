package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Follow;

import java.util.List;

public interface FollowService {
    boolean insert(Follow follow);
    boolean delete(Follow follow);
    List<Follow> selectByFollowerId(String FollowerId);
    List<Follow> selectByFollowingId(String FollowingId);
}
