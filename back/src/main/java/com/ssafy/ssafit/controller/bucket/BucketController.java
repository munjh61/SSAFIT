package com.ssafy.ssafit.controller.bucket;

import com.ssafy.ssafit.model.dto.Bucket;
import com.ssafy.ssafit.model.dto.Img;
import com.ssafy.ssafit.model.service.BoardService;
import com.ssafy.ssafit.model.service.BucketService;
import com.ssafy.ssafit.model.service.ImgService;
import com.ssafy.ssafit.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/bucket")
@RequiredArgsConstructor
public class BucketController {
     private final BucketService bucketService;
     private final ImgService imgService;
     private final BoardService boardService;

     //버킷리스트 등록
     @PostMapping("")
     public ResponseEntity<?> addBucket(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody Bucket bucket){
          String userId = customUserDetails.getUsername();
          bucket.setUserId(userId);
          boolean result = bucketService.addBucket(bucket);

          if(!result){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("등록 실패");
          }

          return ResponseEntity.ok("버킷리스트에 등록되었습니다");
     }

     //버킷리스트 삭제 - bucketList에서
     @DeleteMapping("/{bucketId}")
     public ResponseEntity<?> remove(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable long bucketId){
          String userId = customUserDetails.getUsername(); //로그인 한 유저 정보
          boolean isDeleted = bucketService.removeBucket(bucketId, userId);

          if(!isDeleted){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 실패");
          }
          return ResponseEntity.ok().body("삭제 성공");
     }
     //버킷리스트 삭제 - main에서
     @DeleteMapping("/main/{boardId}")
     public ResponseEntity<?> removeByBoardId(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                              @PathVariable long boardId){
          String userId = customUserDetails.getUsername();
          boolean success = bucketService.removeByBoardId(userId, boardId);

          if (!success) {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 실패");
          }
          return ResponseEntity.ok("삭제 성공");
     }

     //버킷리스트 완료 항목 체크
     @PutMapping("/{bucketId}")
     public ResponseEntity<?> completeBucket(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable long bucketId){
          String userId = customUserDetails.getUsername(); //로그인 한 유저
          boolean isCompleted = bucketService.completeBucket(userId, bucketId);

          System.out.println(userId);

          if(isCompleted){
               return ResponseEntity.ok().body("버킷리스트가 달성되었습니다.");
          }
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("버킷리스트 체크 실패");
     }

     //버킷리스트 출력
     @GetMapping("/list")
     public ResponseEntity<Map<String, Object>> getAllBucket(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
          String userId = customUserDetails.getUsername();
          Map<String, Object> map = new HashMap<>();

          //1. 로그인된 유저의 버킷리스트 불러오기
          List<Bucket> bucketList = bucketService.getBucketByUserId(userId);

          //2. 불러와진 버킷리스트 별 이미지 묶기
          //tag에 값 넣어주기
          Map<Long, List<Img>> bucketImgs = new HashMap<>();
          for (Bucket bucket : bucketList) {
               long boardId = bucket.getBoardId();
               String title = boardService.getTitleByBoardId(boardId);

               List<Img> imgList = imgService.getImgByBoardId(boardId);
               for (Img img : imgList) {
                    img.setTitle(title);
               }

               bucketImgs.put(bucket.getBucketId(), imgList);
          }
          map.put("bucketList", bucketList);
          map.put("bucketImgs", bucketImgs);
          return ResponseEntity.ok(map);
     }

     @GetMapping("/stats")
     public ResponseEntity<?> getStats(@AuthenticationPrincipal CustomUserDetails userDetails) {
          String userId = userDetails.getUsername();
          Map<String, Object> stats = bucketService.getBucketStats(userId);
          return ResponseEntity.ok(stats);
     }

     //버킷 갯수 세기
     @GetMapping("/count/{boardId}")
     public ResponseEntity<Integer> getBucketCount(@PathVariable Long boardId) {
          int count = bucketService.countByBoardId(boardId);
          return ResponseEntity.ok(count);
     }

     //버킷 완료 시간 수정
     @PutMapping("/done/{bucketId}")
     public ResponseEntity<Void> markDoneTime(@PathVariable Long bucketId) {
          bucketService.markDoneTime(bucketId);
          return ResponseEntity.ok().build();
     }
}
