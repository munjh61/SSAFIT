package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Follow;

import java.util.List;

public interface FollowDao {
    int insert(Follow follow);
    Follow selectOne(Follow follow);
    int delete(Follow follow);
    // 서로 팔로우
    List<Follow> selectMutualFollow(String userId);
    // 나만 팔로우
    List<Follow> selectOnlyMe(String userId);
    // 상대만 팔로우
    List<Follow> selectOnlyYou(String userId);
}
