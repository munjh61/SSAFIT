package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Bucket;

import java.util.List;
import java.util.Map;

public interface BucketService {
    //버킷리스트 추가
    boolean addBucket(Bucket bucket);
    //버킷리스트 조회
    List<Bucket> getBucketByUserId(String userId);
    //버킷아이디로 버킷 하나 조회
    Bucket getOneBucket(long bucketId);
    //버킷리스트 삭제-- at bucketList
    boolean removeBucket(long bucketId, String userId);
    //버킷리스트 삭제 -- at main
    boolean removeByBoardId(String userId, long boardId);
    //버킷리스트 체크
    boolean completeBucket(String userId, long bucketId);
    //버킷리스트 통계
    Map<String, Object> getBucketStats(String userId);
    //버킷 갯수
    int countByBoardId(Long boardId);

    //버킷에 들어가있는지 확인
    boolean isBoardInBucket(String userId, Long boardId);

    //버킷 시간
    void markDoneTime(Long bucketId);
}
