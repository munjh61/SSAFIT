package com.ssafy.ssafit.controller.bucket;

import com.ssafy.ssafit.model.dto.Bucket;
import com.ssafy.ssafit.model.service.BucketService;
import com.ssafy.ssafit.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/bucket")
@RequiredArgsConstructor
public class BucketController {
     private final BucketService bucketService;

     //버킷리스트 추가
     @PostMapping("")
     public ResponseEntity<?> addBucket(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody Bucket bucket){
          String userId = customUserDetails.getUsername();
          bucket.setUserId(userId);
          bucketService.addBucket(bucket);
          return ResponseEntity.status(HttpStatus.CREATED).body("버킷리스트에 추가되었습니다");
     }

     //버킷리스트 삭제
     @DeleteMapping("")
     public ResponseEntity<?> remove(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody long bucketId){
          String userId = customUserDetails.getUsername(); //로그인 한 유저 정보
          boolean isDeleted = bucketService.removeBucket(userId, bucketId);
          if(isDeleted){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 실패");
          }
          return ResponseEntity.ok().body("삭제 성공");
     }

     //버킷리스트 완료 항목 체크
     @PutMapping("/{bucketId}")
     public ResponseEntity<?> completeBucket(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable long bucketId){
          String userId = customUserDetails.getUsername(); //로그인 한 유저
          boolean isCompleted = bucketService.completeBucket(userId, bucketId);

          if(isCompleted){
               return ResponseEntity.ok().body("버킷리스트가 달성되었습니다.");
          }
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("버킷리스트 체크 실패");
     }

     //버킷리스트 출력
     @GetMapping("/list")
     public ResponseEntity<List<Bucket>> getAllBucket(@AuthenticationPrincipal CustomUserDetails customUserDetails){
          String userId = customUserDetails.getUsername();
          return ResponseEntity.ok(bucketService.getBucketByUserId(userId));
     }
}
