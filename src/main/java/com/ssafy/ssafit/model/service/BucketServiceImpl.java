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
    public void removeBucket(long bucketId) {
        bucketDao.deleteBucket(bucketId);
    }

    @Override
    public void checkBucket(long bucketId) {
        bucketDao.checkBucket(bucketId);
    }
}
