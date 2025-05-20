package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Bucket;

import java.util.List;

public interface BucketService {
    //버킷리스트 추가
    void addBucket(Bucket bucket);
    //버킷리스트 조회
    List<Bucket> getBucketByUserId(String userId);
    //버킷리스트 삭제
    void removeBucket(long bucketId);
    //버킷리스트 체크
    void checkBucket(long bucketId);
}
