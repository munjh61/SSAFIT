package com.ssafy.ssafit.controller.follow;

import com.ssafy.ssafit.model.dto.Follow;
import com.ssafy.ssafit.model.service.FollowService;
import com.ssafy.ssafit.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followerId}")
    public ResponseEntity<String> addFollow(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("followerId") String followerId) {
        Follow follow = new Follow();
        // 내가 팔로우를 걸면, 내가 상대방의 팔로잉이 되는 거고, 내 기준 상대가 팔로워가 된다.
        follow.setFollowingId(followerId);
        follow.setFollowerId(userDetails.getUsername());
        if (!followService.insert(follow)) {
            return new ResponseEntity<>("팔로우 생성 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("팔로우 생성 성공", HttpStatus.CREATED);
    }

    @DeleteMapping("/{followerId}")
    public ResponseEntity<String> deleteFollow(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("followerId") String followerId) {
        Follow follow = new Follow();
        follow.setFollowingId(followerId);
        follow.setFollowerId(userDetails.getUsername());
        if (!followService.delete(follow)) {
            return new ResponseEntity<>("팔로우 삭제 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("팔로우 삭제 성공", HttpStatus.CREATED);
    }
}
