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
public class FollowServiceImpl implements FollowService{

    private final FollowDao followDao;
    private final UserDao userDao;

    @Override
    public boolean insert(Follow follow) {
        if(userDao.select(follow.getFollowingId())==null)
            return false;
        if(followDao.selectOne(follow) != null){
            return false;
        }
        followDao.insert(follow);
        return true;
    }

    @Override
    public boolean delete(Follow follow) {
        return followDao.delete(follow)>0;
    }

    @Override
    public Map<String, List<Follow>> followList(String userId) {
        Map<String, List<Follow>> map = new HashMap<>();
        // 없으면 size가 0 인 list임
        map.put("mutual",followDao.selectMutualFollow(userId));
        map.put("onlyMe", followDao.selectOnlyMe(userId));
        map.put("onlyYou", followDao.selectOnlyYou(userId));
        return map;
    }


}
