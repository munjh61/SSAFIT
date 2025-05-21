package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Bucket;

import java.util.List;

public interface BucketService {
    //버킷리스트 추가
    void addBucket(Bucket bucket);
    //버킷리스트 조회
    List<Bucket> getBucketByUserId(String userId);
    //버킷아이디로 버킷 하나 조회
    Bucket getOneBucket(long bucketId);
    //버킷리스트 삭제
    boolean removeBucket(String userId, long bucketId);
    //버킷리스트 체크
    boolean completeBucket(String userId, long bucketId);
}
