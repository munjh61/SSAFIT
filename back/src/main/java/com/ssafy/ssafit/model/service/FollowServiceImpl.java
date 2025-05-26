package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.FollowDao;
import com.ssafy.ssafit.model.dao.UserDao;
import com.ssafy.ssafit.model.dto.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowDao followDao;
    private final UserDao userDao;

    @Override
    public boolean insert(Follow follow) {
        if (userDao.select(follow.getFollowingId()) == null) {
            return false;
        }
        // follower, following 으로 찾아서 이미 존재하는지 확인
        if (followDao.selectOne(follow) != null) {
            return false;
        }
        followDao.insert(follow);
        return true;
    }

    @Override
    public boolean delete(Follow follow) {
        Follow tmp = followDao.selectOne(follow);
        if (tmp == null) {
            return false;
        }
        return followDao.delete(tmp) > 0;
    }

    @Override
    public Map<String, List<Follow>> followList(String userId) {
        Map<String, List<Follow>> map = new HashMap<>();
        // 없으면 size가 0 인 list임
        map.put("mutual", followDao.selectMutualFollow(userId));
        map.put("onlyMe", followDao.selectOnlyMe(userId));
        map.put("onlyYou", followDao.selectOnlyYou(userId));
        return map;
    }

    @Override
    public boolean isFollowed(String followerId, String followingId) {
        Follow follow = Follow.builder()
                .followerId(followerId)
                .followingId(followingId)
                .build();
        return followDao.selectOne(follow) != null;
    }

}
