package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.BucketDao;
import com.ssafy.ssafit.model.dto.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService {

    private final BucketDao bucketDao;

    @Override
    public void addBucket(Bucket bucket) {
        bucketDao.insertBucket(bucket);
    }

    @Override
    public List<Bucket> getBucketByUserId(String userId) {
        return bucketDao.selectBucketByUserId(userId);
    }

    @Override
    public Bucket getOneBucket(long bucketId) {
        return bucketDao.selectBucketByBucketId(bucketId);
    }

    @Override
    public boolean removeBucket(String userId, long bucketId) {
        //해당 버킷리스트 작성한 유저가 로그인 된 유저인지 확인
        Bucket bucket = bucketDao.selectBucketByBucketId(bucketId);
        String tmp = bucket.getUserId(); //버킷 추가한 유저
        if(!userId.equals(tmp)){
            return false;
        }
        bucketDao.deleteBucket(bucketId);
        return true;
    }

    @Override
    public boolean completeBucket(String userId, long bucketId) {
        //해당 버킷이 로그인한 유저에게 해당하는 버킷인지 확인
        Bucket bucket = bucketDao.selectBucketByBucketId(bucketId); //수정하려는 bucket
        String tmp = bucket.getUserId(); //해당 버킷을 작성한 유저
        if(!userId.equals(tmp)){
            return false;
        }
        bucketDao.completeBucket(bucketId);
        return true;
    }
}
