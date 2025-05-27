package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Bucket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BucketDao {

    //버킷리스트 추가
    boolean insertBucket(Bucket bucket);
    //버킷리스트 userId로 조회
    List<Bucket> selectBucketByUserId(String userId);
    //버킷리스트 bucketId로 조회
    Bucket selectBucketByBucketId(Long bucketId);
    //버킷리스트 삭제
    int deleteBucket(@Param("bucketId") Long bucketId, @Param("userId") String userId);
    //버킷리스트 삭제 -- at main
    int deleteByBoardId(@Param("userId")String userId, @Param("boardId") Long boardId);
    //버킷리스트 체크
    int completeBucket(@Param("bucketId") long bucketId);
    //버킷리스트 통계
    int countByUserId(String userId);
    int countDoneByUserId(String userId);

    //버킷 갯수
    int countByBoardId(Long boardId);

    //버킷에 존재하는거인지
    boolean exist(String userId, Long boardId);
}
