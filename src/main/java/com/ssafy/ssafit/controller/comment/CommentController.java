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

@Slf4j
@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<?> update(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable long commentId, @RequestBody Comment comment){
        //Authorization
        //Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoidXNlciIsInVzZXJOYW1lIjoi67CV7ZSE66Gg7Yq4IiwidXNlcklkIjoidXNlcjAzIiwiaWF0IjoxNzQ3NzIxMjYyLCJleHAiOjE3NDg5MzA4NjJ9.Au6-Ze8jG78wUgcEW0LEcdwno5t-Gzvw0GBC37MuomM
        //http://localhost:8080/api/auth/login
        comment.setCommentId(commentId);
        String userId = customUserDetails.getUsername(); //로그인한 유저
        boolean isUpdated = commentService.modifyComment(userId, comment);
        if(isUpdated){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("댓글 수정 실패");
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable long commentId){
        //삭제하려는 댓글을 작성한 유저가 로그인된 유저랑 같은지 확인
        String userId = customUserDetails.getUsername();
        boolean isDeleted = commentService.removeComment(userId, commentId);

        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body("comment delete");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed");
    }
}
