package com.ssafy.ssafit.controller.comment;

import com.ssafy.ssafit.model.dto.Comment;
import com.ssafy.ssafit.model.service.CommentService;
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
@RequestMapping("api/public/comment")
@RequiredArgsConstructor
public class CommentPublicController {
    private final CommentService commentService;

    //댓글 조회
    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<Comment>> getComment(@PathVariable long boardId){
        List<Comment> list = commentService.getAllComment(boardId);
        return ResponseEntity.ok(list);
    }

    //댓글 작성
    @PostMapping("/board/{boardId}")
    public ResponseEntity<?> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody Comment comment, @PathVariable long boardId){
        //입력받은 comment가 null 값인지 확인
        comment.setBoardId(boardId);
        String userId = customUserDetails.getUsername();
        comment.setUserId(userId);
        String content = comment.getContent();
        if(content == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("댓글 내용이 빈칸입니다");
        }
        commentService.createComment(comment, boardId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
