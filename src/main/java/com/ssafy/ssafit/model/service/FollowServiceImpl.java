package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.FollowDao;
import com.ssafy.ssafit.model.dto.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    private final FollowDao followDao;

    @Override
    public boolean insert(Follow follow) {
        if(followDao.selectOneByFollowerIdAndFollowingId(follow) != null){
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
    public List<Follow> selectByFollowerId(String FollowerId) {
        return List.of();
    }

    @Override
    public List<Follow> selectByFollowingId(String FollowingId) {
        return List.of();
    }
}
