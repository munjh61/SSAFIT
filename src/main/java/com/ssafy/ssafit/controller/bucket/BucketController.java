package com.ssafy.ssafit.controller.bucket;

import com.ssafy.ssafit.model.dto.Bucket;
import com.ssafy.ssafit.model.service.BucketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     public ResponseEntity<?> addBucket(@RequestBody Bucket bucket){
          bucketService.addBucket(bucket);
          return ResponseEntity.status(HttpStatus.CREATED).build();
     }

     //버킷리스트 삭제
     @DeleteMapping("")
     public ResponseEntity<?> removeBucket(@RequestBody long bucketId){
          bucketService.removeBucket(bucketId);
          return ResponseEntity.ok().build();
     }

     //버킷리스트 완료 항목 체크
     @PutMapping("/{bucketId}")
     public ResponseEntity<?> checkBucket(@PathVariable long bucketId){
          bucketService.checkBucket(bucketId);
          return ResponseEntity.ok().build();
     }

     @GetMapping("/list")
     public ResponseEntity<List<Bucket>> getAllBucket(@RequestParam String userId){
          return ResponseEntity.ok(bucketService.getBucketByUserId(userId));
     }
}
