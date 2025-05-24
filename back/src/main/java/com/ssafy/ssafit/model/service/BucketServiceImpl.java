package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.BucketDao;
import com.ssafy.ssafit.model.dto.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService {

    private final BucketDao bucketDao;

    @Override
    public boolean addBucket(Bucket bucket) {
        return bucketDao.insertBucket(bucket);
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
    public boolean removeBucket(long bucketId, String userId) {
        Bucket bucket = bucketDao.selectBucketByBucketId(bucketId);
        //1. 해당 버킷이 진짜 있는지 확인
        if(bucket == null){
            return false;
        }
        String tmp = bucket.getUserId(); //버킷에 할당된 유저값
        System.out.println("bucket.userId=" + tmp + ", 로그인 userId=" + userId);
        //2. 로그인 된 유저의 버킷인지 확인
        if(!userId.equals(tmp)){
            return false;
        }
        //3. 삭제 실행
        int deleted = bucketDao.deleteBucket(bucketId, userId);
        System.out.println("실제 삭제된 row 수: " + deleted);
        //4. 파라미터 전달
        return deleted>0;
    }

    @Override
    public boolean removeByBoardId(String userId, long boardId) {
        System.out.println("🧾 삭제 시도 - userId: " + userId + ", boardId: " + boardId);
        return bucketDao.deleteByBoardId(userId, boardId) > 0;
    }

    @Override
    public boolean completeBucket(String userId, long bucketId) {
        //해당 버킷이 로그인한 유저에게 해당하는 버킷인지 확인
        Bucket bucket = bucketDao.selectBucketByBucketId(bucketId); //수정하려는 bucket
        String tmp = bucket.getUserId(); //해당 버킷을 작성한 유저
        if(!userId.equals(tmp)){
            return false;
        }
        return bucketDao.completeBucket(bucketId) > 0;
    }

    @Override
    public Map<String, Object> getBucketStats(String userId) {
        int total = bucketDao.countByUserId(userId);
        int done = bucketDao.countDoneByUserId(userId);
        int doing = total - done;
        int rate = total == 0 ? 0 : (done * 100) / total;

        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("done", done);
        result.put("doing", doing);
        result.put("rate", rate);
        return result;
    }


}
