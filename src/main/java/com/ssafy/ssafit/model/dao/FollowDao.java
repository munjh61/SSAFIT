package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Follow;

import java.util.List;

public interface FollowDao {
    List<Follow> selectByFollowerId(String FollowerId);
    List<Follow> selectByFollowingId(String FollowingId);
    Follow selectOneByFollowerIdAndFollowingId(Follow follow);
    int insert(Follow follow);
    int delete(Follow follow);
}
