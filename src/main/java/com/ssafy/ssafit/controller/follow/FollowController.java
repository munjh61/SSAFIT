package com.ssafy.ssafit.controller.follow;

import com.ssafy.ssafit.model.dto.Follow;
import com.ssafy.ssafit.model.service.FollowService;
import com.ssafy.ssafit.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followingId}")
    public ResponseEntity<String> addFollow(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("followingId") String followingId) {
        Follow follow = new Follow();
        // 내가 팔로우를 걸면, 내가 팔로워가 되는 거고, 상대가 팔로잉이 된다.
        follow.setFollowingId(followingId);
        follow.setFollowerId(userDetails.getUsername());
        if (!followService.insert(follow)) {
            return new ResponseEntity<>("팔로우 생성 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("팔로우 생성 성공", HttpStatus.CREATED);
    }

    @DeleteMapping("/{followingId}")
    public ResponseEntity<String> deleteFollow(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("followingId") String followingId) {
        Follow follow = new Follow();
        follow.setFollowingId(followingId);
        follow.setFollowerId(userDetails.getUsername());
        if (!followService.delete(follow)) {
            return new ResponseEntity<>("팔로우 삭제 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("팔로우 삭제 성공", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Follow>>> followList(@AuthenticationPrincipal CustomUserDetails userDetails){
        return new ResponseEntity<>(followService.followList(userDetails.getUsername()), HttpStatus.OK);
    }
}
