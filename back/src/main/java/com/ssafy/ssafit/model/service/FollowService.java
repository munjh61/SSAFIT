package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Follow;

import java.util.List;
import java.util.Map;

public interface FollowService {
    boolean insert(Follow follow);
    boolean delete(Follow follow);
    Map<String,List<Follow>> followList(String userId);
    boolean isFollowed(String targetUserId,String loginUserId);
    List<String> getFollowingUserIds(String userId);
    List<Follow> getRecommendedUsers(String userId);
}
