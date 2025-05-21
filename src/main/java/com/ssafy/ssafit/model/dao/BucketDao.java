package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Bucket;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BucketDao {

    //버킷리스트 추가
    void insertBucket(Bucket bucket);
    //버킷리스트 userId로 조회
    List<Bucket> selectBucketByUserId(String userId);
    //버킷리스트 bucketId로 조회
    Bucket selectBucketByBucketId(long bucketId);
    //버킷리스트 삭제
    void deleteBucket(long bucketId);
    //버킷리스트 체크
    void completeBucket(long bucketId);


}
