package com.ssafy.ssafit.controller.follow;

import com.ssafy.ssafit.model.dto.Follow;
import com.ssafy.ssafit.model.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/public/follow")
@RequiredArgsConstructor
public class FollowPublicController {

    private final FollowService followService;

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, List<Follow>>> followList(@PathVariable("userId") String userId){
        return new ResponseEntity<>(followService.followList(userId), HttpStatus.OK);
    }
}
